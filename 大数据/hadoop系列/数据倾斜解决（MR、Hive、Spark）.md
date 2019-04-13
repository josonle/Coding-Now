### 数据倾斜是什么

总的来说，你集群运行时发现MR或者Hive卡在99%不动时，或者Spark运行时出现OOM异常（OutOfMemoryError），或者成功执行但就是耗时过久时，既要考虑是否出现了数据倾斜。数据倾斜就是某些key对应的数据分化不均，导致部分reduce处理数据量过大，以至于其他reduce都执行完了它还在运行。

> 查看web UI、日志文件：
>
> 看是否出现map和reduce任务执行时间过久，需要缓存数据集的操作消耗过多的内存资源

map端也是会出现数据倾斜的

### MapReduce

#### 减少Reduce数据倾斜

1. 考虑map端使用Combine提前聚合部分数据

2. 自定义分区，避免将过大数据量分发到某些reduce

   > MR默认分区方式是通过map输出的key的hash值来分发到某些reduce上的，数据分布均匀的话结果还好，但出现数据倾斜就是个问题
   >
   > - 可以通过对原始数据进行抽样得到的结果集来预设分区边界值。TotalOrderPartitioner中的范围分区器可以通过预设的分区边界值进行分区。因此它也可以很好地用在矫正数据中的部分键的数据倾斜问题
   > - 像Hive那样，在出现数据倾斜的Key上加上随机前缀，先进行第一次局部聚合减少数据量。再去掉前缀进行第二次MapReduce全局聚合

3. 涉及数据连接时，可以预习考虑在map端连接

> 4. 数据大小倾斜的自定义策略
>
> 在map端或reduce端的数据大小倾斜都会对缓存造成较大的影响，乃至导致OutOfMemoryError异常。处理这种情况并不容易。可以参考以下方法。
>
> - 设置`mapred.linerecordreader.maxlength`来限制RecordReader读取的最大长度。RecordReader在TextInputFormat和KeyValueTextInputFormat类中使用。默认长度没有上限。
> - 通过`org.apache.hadoop.contrib.utils.join`设置缓存的数据集的记录数上限。在reduce中默认的缓存记录数上限是100条。
> - 考虑使用有损数据结构压缩数据，如Bloom过滤器。
>
> > 这里我不太理解，没有实际应用过吧。参考：<https://www.cnblogs.com/datacloud/p/3601624.html>

#### 预判MapReduce中哪些key会出现数据倾斜

在reduce方法中加入记录map输出键的详细情况的功能。



在发现了倾斜数据的存在之后，就很有必要诊断造成数据倾斜的那些键。有一个简便方法就是在代码里实现追踪每个键的最大值。为了减少追踪量，可以设置数据量阀值，只追踪那些数据量大于阀值的键，并输出到日志中。实现代码如下。

```java
public static final String MAX_VALUES = "skew.maxvalues";
private int maxValueThreshold;

@Override
public void configure(JobConf job) {
    maxValueThreshold = job.getInt(MAX_VALUES, 100);
}

@Override
public void reduce(Text key, Iterator<Text> values,
                    OutputCollector<Text, Text> output, 
                    Reporter reporter) throws IOException {
                    
    int i = 0;
    
    while (values.hasNext()) {
        values.next();
        i++;
    }
    
    if (++i > maxValueThreshold) {
        log.info("Received " + i + " values for key " + key);
    }
}
```

运行作业后就可以从日志中判断发生倾斜的键以及倾斜程度。

跟踪倾斜数据是了解数据的重要一步，也是设计MapReduce作业的重要基础。

>  此处参考自： [MapReduce 性能调优：减小数据倾斜的性能损失](https://www.cnblogs.com/datacloud/p/3601624.html)

### Hive

hive底层也是MapReduce，所以数据倾斜问题的解决也和上面差不多。但hive的数据倾斜多发生在group聚合、join连接上（这些会触发shuffle操作），可以通过配置几个参数来解决

#### groupBy上数据倾斜解决

```
set hive.map.aggr=true；//开启map端聚合，默认true
set hive.groupby.skewindata=true; //有数据倾斜时进行负载均衡，默认false
set hive.groupby.mapaggr.checkinterval = 100000; //map端聚合操作条目数目
```

**注意下这几个万金油**：

- map端聚合（mapjoin）
- 负载均衡

> 负载均衡：
>
> 生成的查询计划会有两个MRJob。第一个MRJob 中，Map的输出结果集合会随机分布到Reduce中，每个Reduce做部分聚合操作，并输出结果，这样处理的结果是相同的GroupBy Key有可能被分发到不同的Reduce中，从而达到负载均衡的目的；第二个MRJob再根据预处理的数据结果按照GroupBy Key分布到Reduce中（这个过程可以保证相同的GroupBy Key被分布到同一个Reduce中），最后完成最终的聚合操作
>
> 
>
> 这个也是给Key加上标志，但好像不是上面说的key加上前缀

还有就是像Spark算子中，用reduceByKey取代groupByKey，这也是为了避免reduce中出现OOM问题。所以，作count、count distinct之前先对key进行一次group

#### join上解决数据倾斜

- 遵循小表join大表原则

join中执行顺序从左往右，其次join的reduce阶段会把左表加载到内存中，小表可以降低内存溢出的可能

- 先过滤再join，以便减少join的数据量
- mapJoin：`hive.auto.convert.join = true`设置自动选择mapjoin，默认true；设置小表阈值`hive.mapjoin.smalltable.filesize=25000000`（默认25M以下是小表）

mapJoin的好处是，不进行shuffle。hive会自动识别比较小的表，继而用mapJoin来实现两个表的联合



> 不说数据倾斜，推荐一篇之前看过的Hive优化文章——[参考这篇文章](https://blog.csdn.net/MrLevo520/article/details/76339075)

### Spark

其实都差不多，mapJoin、repartition/coalesce调整reduce数量（也就是调整并行度）、自定义分区器、Key加上随机前缀等



- 调整并行度	【第一步先尝试这个】

  - spark算子上加上partition分区数，因为一个分区对应一个reduce。而根据map的输出的hash值对reduce数取模分发到不同reduce上，所以改变reduce数目可以缓解倾斜问题

  - 也可在配置文件中通过`spark.default.parallelism`设置并行度

  - 通过coalesce、repartition算子重新调整分区数

    ```scala
    def coalesce(numPartitions: Int, shuffle: Boolean = false)(implicit ord: Ordering[T] = null): RDD[T]
    //numPartitions：重分区数，shuffle：是否进行shuffle，默认false
    //通过HashPartitioner对RDD进行重分区
    def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]
    //同上，不过默认是进行shuffle的
    //rdd.partitions.size可以查看分区数
    ```

    

  - spark sql通过`spark.sql.shuffle.partitions`设置并行度

**调整并行度并没有完全解决数据倾斜的问题，只是缓解了**

- 自定义分区器Partitioner

  > **优势**
  > 不影响原有的并行度设计。如果改变并行度，后续Stage的并行度也会默认改变，可能会影响后续Stage。
  >
  > **劣势**
  > 适用场景有限，只能将不同Key分散开，对于同一Key对应数据集非常大的场景不适用。效果与调整并行度类似，只能缓解数据倾斜而不能完全消除数据倾斜。而且需要根据数据特点自定义专用的Partitioner，不够灵活。

- mapJoin

- 发生倾斜的key加上随机前缀

- Spark Sql中的优化和Hive类似，可以参考Hive

Spark调优我没怎么实操过，不太熟悉。放出参考的几篇文章吧

- [Spark性能优化之道——解决Spark数据倾斜（Data Skew）的N种姿势](<http://www.jasongj.com/spark/skew/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io>)
- [Spark性能优化：数据倾斜调优](<https://www.iteblog.com/archives/1671.html>)
- [Spark性能优化指南——基础篇](https://tech.meituan.com/2016/04/29/spark-tuning-basic.html)
- [Spark性能优化指南——高级篇](https://tech.meituan.com/2016/05/12/spark-tuning-pro.html)

***

分割线

***

### 参考

- [漫谈千亿级数据优化实践：数据倾斜（纯干货）](https://segmentfault.com/a/1190000009166436)
- [MapReduce 性能调优：理解性能瓶颈，诊断map性能瓶颈](https://www.cnblogs.com/datacloud/p/3591981.html)
- 其余参考列举在文中

