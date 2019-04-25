> 今天订阅的RSS给我推送了一篇文章——在大数据中规模应用 pandas 切换Spark，感觉挺新鲜的，原文地址 https://www.infoq.cn/article/tvGrtwJxCR1kQDs_kqa4?utm_source=rss&utm_medium=article

> 4 月 24 日，Databricks 在 Spark + AI 峰会上开源了一个新产品 Koalas，它增强了 PySpark 的 DataFrame API，使其与 pandas 兼容。

Python 数据科学在过去几年中爆炸式增长， pandas 已成为生态系统的关键。当数据科学家得到一个数据集时，他们会使用 pandas 进行探索。它是数据处理和分析的终极工具。事实上，pandas 的 read_csv 通常是学生在学习数据科学过程中使用的第一个命令。

问题是什么呢？pandas 不能很好地在大数据中规模应用，因为它专为单个机器可以处理的小型数据集而设计。另一方面，Apache Spark 已成为处理大数据实际上的标准。今天，许多数据科学家将 pandas 用于职业培训、偏好性项目和小型数据任务，但是当他们使用非常大的数据集时，他们必须迁移到 PySpark 以利用 Spark，或对其数据进行下采样以使用 pandas。

现在有了 Koalas，数据科学家可以从单个机器迁移到分布式环境，而无需学习新的框架。正如你在下面所看到的，只需替换一个包，就可以使用 Koalas 在 Spark 上扩展你的 pandas 代码。

pandas：

```python
import pandas as pd
df = pd.DataFrame({'x': [1, 2], 'y': [3, 4], 'z': [5, 6]})
# Rename columns
df.columns = [‘x’, ‘y’, ‘z1’]
# Do some operations in place
df[‘x2’] = df.x * df.x

```

Koalas:

```python
import databricks.koalas as ks
df = ks.DataFrame({'x': [1, 2], 'y': [3, 4], 'z': [5, 6]})
# Rename columns
df.columns = [‘x’, ‘y’, ‘z1’]
# Do some operations in place
df[‘x2’] = df.x * df.x

```

## pandas 作为 Python 数据科学的标准词汇

随着 Python 成为数据科学的主要语言，社区已经开发了基于最重要的库的词汇表，包括 pandas、matplotlib 和 numpy。当数据科学家能够使用这些库时，他们可以充分表达他们的想法，并根据想法得出结论。他们可以将某些东西概念化并立即执行。

但是当他们不得不在词汇表之外使用库时，他们会遇到麻烦，每隔几分钟就需要检查一次 StackOverflow，还必须中断他们的工作流程才能使代码正常工作。虽然 PySpark 易于使用，并且在很多方面类似于 pandas，但它仍然是一个数据科学家必须学习的不同词汇。

在 Databricks，我们相信 Spark 上的 pandas 将大大提高数据科学家和数据驱动型组织的生产力，原因如下：

*   Koalas 无需决定是否为给定的数据集使用 pandas 或 PySpark
*   对于最初用 pandas 为单个机器编写的工作，Koalas 允许数据科学家通过 pandas 和 Koalas 的轻松切换来扩展在 Spark 上的代码
*   Koalas 为组织中的更多数据科学家解锁大数据，因为他们不再需要学习 PySpark 以使用 Spark

下面，我们展示了两个简单而强大的 pandas 方法示例，这些方法可以直接在 Spark 上运行 Koalas。

## 具有分类变量的特征工程

数据科学家在构建 ML 模型时经常会遇到分类变量。一种流行的技术是将分类变量编码为虚拟变量。在下面的示例中，有几个分类变量，包括调用类型、邻域和单元类型。pandas 的 get_dummies 是一种简便的方法。下面我们将展示如何使 

pandas：

```python
import pandas as pd
data = pd.read_csv("fire_department_calls_sf_clean.csv", header=0)
display(pd.get_dummies(data))

```

![](https://static.geekbang.org/infoq/5cc121148d2bf.png)
![](https://static.geekbang.org/infoq/5cc12115447e7.png)
有了 Koalas，我们可以通过一些调整在 Spark 上做到这一点：

```python
import databricks.koalas as ks
data = ks.read_csv("fire_department_calls_sf_clean.csv", header=0)
display(ks.get_dummies(data))

```

就是这样！

## 带时间戳的算术

数据科学家一直使用时间戳，但正确处理这些时间戳非常麻烦。pandas 提供了一个优雅的解决方案。假设你有一个日期的 DataFrame ：

```python
import pandas as pd
import numpy as np
date1 = pd.Series(pd.date_range('2012-1-1 12:00:00', periods=7, freq='M'))
date2 = pd.Series(pd.date_range('2013-3-11 21:45:00', periods=7, freq='W'))
df = pd.DataFrame(dict(Start_date = date1, End_date = date2))
print(df)

  End_date            Start_date
0 2013-03-17 21:45:00 2012-01-31 12:00:00
1 2013-03-24 21:45:00 2012-02-29 12:00:00
2 2013-03-31 21:45:00 2012-03-31 12:00:00
3 2013-04-07 21:45:00 2012-04-30 12:00:00
4 2013-04-14 21:45:00 2012-05-31 12:00:00
5 2013-04-21 21:45:00 2012-06-30 12:00:00
6 2013-04-28 21:45:00 2012-07-31 12:00:00

```

要使用 pandas 从结束日期中减去开始日期，只需运行：

```python
df['diff_seconds'] = df['End_date'] - df['Start_date']
df['diff_seconds'] = df['diff_seconds']/np.timedelta64(1,'s')
print(df)
End_date Start_date diff_seconds
0 2013-03-17 21:45:00 2012-01-31 12:00:00 35545500.0
1 2013-03-24 21:45:00 2012-02-29 12:00:00 33644700.0
2 2013-03-31 21:45:00 2012-03-31 12:00:00 31571100.0
3 2013-04-07 21:45:00 2012-04-30 12:00:00 29583900.0
4 2013-04-14 21:45:00 2012-05-31 12:00:00 27510300.0
5 2013-04-21 21:45:00 2012-06-30 12:00:00 25523100.0
6 2013-04-28 21:45:00 2012-07-31 12:00:00 23449500.0

```

现在要在 Spark 上做同样的事情，你需要做的就是用 Koalas 替换 pandas：

复制代码

```python
import databricks.koalas as ks
df = ks.from_pandas(pandas_df)
df['diff_seconds'] = df['End_date'] - df['Start_date']
df['diff_seconds'] = df['diff_seconds'] / np.timedelta64(1,'s')
print(df)

```

再做一次，就这么简单。

## 接下来的计划和 Koalas 入门

我们之所以创建 Koalas，是因为我们遇到了许多不愿意处理大数据的数据科学家。我们相信 Koalas 会让数据科学家将工作轻松拓展到 Spark 上，从而让他们获得更多能力。

目前为止，我们已经实现了常见的 DataFrame 操作方法，以及 pandas 中强大的索引技术。以下是我们路线图中的一些即将推出的项目，主要侧重于扩大覆盖范围：

*   用于处理文本数据的字符串操作
*   时间序列数据的日期 / 时间操作

该计划尚处于初期阶段，但正在迅速发展。如果你有兴趣了解更多有关 Koalas 或入门的信息，请查看该项目的 **GitHub：**[https://github.com/databricks/koalas](https://github.com/databricks/koalas)



> 原文链接：
> [https://databricks.com/blog/2019/04/24/koalas-easy-transition-from-pandas-to-apache-spark.html](https://databricks.com/blog/2019/04/24/koalas-easy-transition-from-pandas-to-apache-spark.html)