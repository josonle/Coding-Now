### Class文件和类结构

类初始化时会谈及加载、验证过程，后者会检查class文件是否符合JVM的规定。JVM规范严格定义了CLASS文件的格式

### 类加载过程

类是怎样被JVM使用的？JVM会先找到类的入口，也就是main方法，通过JNI方法和Class类对象沟通

#### 加载

第一步就是**加载**：首先是找到类（通过类的全限定名来获取定义该类的二进制流），然后通过**类加载器**将类装载到JVM实例中（具体是通过IO从硬盘或者网络读取读取Class二进制文件，将字节流中的静态存储结构转为方法区中运行时数据结构，并在内存中生成一个该类的Class对象作为类中数据结构的入口）。本质上就是把class文件读取到内存中

> #### 聊下类加载器和双亲委派机制
>
> JVM启动时会把JRE默认的一些类加载到内存，这部分类（JRE提供的底层所需的类）使用的加载器是JVM默认内置的由C/C++实现
>
> - Bootstrap ClassLoader：包含在JVM内的，由C++实现，负责加载`JAVA_HOME/lib`下或者`-Xbootclasspath`指定路径下的类（实际是 `/jre/lib`下的类）
>
>   > `URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();`
>
> - Extension ClassLoader：独立于JVM外，Java实现的，加载`JAVA_HOME/lib/ext`下或者`java.ext.dirs`系统变量指定的路径下的类（实际打印可以发现是`jre/lib/ext`下的类）
>
> ![](assets/深度截图_选择区域_20190509091416.png)
>
> - Application ClassLoader：独立于JVM外，java实现，加载`-classpath`用户路径下的类（具体类加载器是`sun.misc.Launcher$AppClassLoader`）
>
> - 用户自定义类加载器：加载第三方类，具体是继承ClassLoader类，重写findClass和loadClass方法来自定义获取CLASS文件的目的
>
> ```java
> //ClassLoader中loadClass方法
> protected Class<?> loadClass(String name, boolean resolve)
>  throws ClassNotFoundException
> {
>  synchronized (getClassLoadingLock(name)) {
>      // First, check if the class has already been loaded，看缓存有没有没有才去找
>      Class<?> c = findLoadedClass(name);
>      if (c == null) {
>          long t0 = System.nanoTime();
>          try {
>              //先看是不是最顶层，如果不是则parent为空，然后获取父类
>              if (parent != null) {
>                  c = parent.loadClass(name, false);
>              } else {
>                  //如果为空则说明应用启动类加载器，让它去加载
>                  c = findBootstrapClassOrNull(name);
>              }
>          } catch (ClassNotFoundException e) {
>              // ClassNotFoundException thrown if class not found
>              // from the non-null parent class loader
>          }
>          if (c == null) {
>              // If still not found, then invoke findClass in order
>              //如果还是没有就调用自己的方法，确保调用自己方法前都使用了父类方法，如此递归三次到顶
>              long t1 = System.nanoTime();
>              c = findClass(name);
>              // this is the defining class loader; record the stats
>              sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
>              sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
>              sun.misc.PerfCounter.getFindClasses().increment();
>          }
>      }
>      if (resolve) {
>          resolveClass(c);
>      }
>      return c;
>  }
> }
> protected Class<?> findClass(String name) throws ClassNotFoundException {
>  throw new ClassNotFoundException(name);
> }
> ```
>
> 
>
> - 双亲委派模型
>
> 定义不细说，就是类加载器优先把类加载请求交付给父类加载器去加载，层层递归，直到父类加载器无法加载才尝试自己加载该类（最多迭代三次可达到BootStrap ClassLoader）。
>
> 好处是类有了层次性划分，不重复加载已有的类以及维护类的唯一性，因为**类本身和加载这个类的类加载器来确定这个类在JVM中的唯一性**，比如书中举例`java.lang.Object`层层交付会由BootStrap ClassLoader加载（到JAVA_HOME/lib下rt.jar中去找），哪怕你自己写了个`java.lang.Object`还是会层层交付最终还是BootStrap ClassLoader来加载，使得JVM中只会出现一个`java.lang.Object`对象。如果不双亲委派可能会出现多个Object对象，整个系统就乱套了
>
> - 破坏双亲委派机制

- 何时会触发类加载器加载class文件

继承场景，子类的创建触发父类加载；反射场景，通过对象反射获取类对象；类作为入口场景，即包含main方法的类首先会被加载；通过new创建对象、直接调用static属性、方法、代码块的场景（getstatic/putstatic/invokestatic字节码指令）

#### 验证、准备、解析 【归属于链接阶段】

class文件被加载到内存中后并非直接使用，想想就知道，要是不符合规范不就是留了个后门安全隐患

- 验证不通过会报VerifyError。 `- Xverify:none `参数可以关闭验证，可见验证阶段非强制的
- 准备，即验证通过后为static变量分配内存并赋零值(0、null这些)，不过遇到静态常量时会赋初值。准备阶段更不一定会发生，比如没有static变量
- 解析，引用字面量转化为直接变量空间  【？？？？？？**具体干啥待细看**】



#### 初始化

把变量的初始值和构造方法的内容初始化到变量的空间里面

#### 使用

#### 卸载

### JMM（运行时内存分布）



### 垃圾回收

### 对象的四种引用及使用场景

强（**StrongReference**）、软（**SoftReference**）、弱（**WeakReference**）、虚（**PhantomReference**）引用

强引用，`A a = new A();`引用对象a就是强引用，不管内存够不够都不会被GC回收

软引用，`SoftReference<Object> softReference = new SoftReference<Object>(new Object());`常用于本地缓存，内存不足时会被回收

弱引用，`WeakReference<Object> weakReference = new WeakReference<Object>(new Object());`一旦发生GC就会被回收，也可用于本地缓存（**但多不用，因为弱引用会导致GC升高，软引用在内存足够时对GC无影响**，本地cache的场景下尽量使用软引用减少对GC产生的影响）

> ### 解决引用导致GC升高问题
>
> `-XX:+PrintReferenceGC`可以查看哪种引用处理时间长
>
> `jmap -dump`再通过dump堆内内存镜像，后续通过MAT查找相关引用并定位到相关代码，对代码优化
>
> `-XX:+ParallelRefProcEnabled`无法优化代码时，可通过该参数来加快引用处理

虚引用，这种引用好比没有，其意义也就是在引用对象回收时返回一个信息，多用来跟踪对象何时被回收，防止资源泄露等

引用对象的回收不是一次GC就能解决的，像软、弱、虚引用都会对应一个引用队列，GC第一次扫描会将引用对象放入队列中，第二次GC时才会回收引用对象

#### 参考

- [从一个Young GC变慢的案例来聊聊finalize方法](<https://mp.weixin.qq.com/s?__biz=MzUzODQ0MDY2Nw==&mid=2247483864&idx=1&sn=aced9b023aeb1fdd15a234a10e419cac&chksm=fad6e76ccda16e7a20cbb1862cfb0aa21284facd1b9b9150118a7094e21807d8c92ef08aa216&scene=21#wechat_redirect>)
  - 看不太懂，涉及gc的分析，没有实际经验
- [你确定你真的了解Java四种引用（强引用、弱引用、软引用、虚引用）了吗？](<https://mp.weixin.qq.com/s?__biz=MzUzODQ0MDY2Nw==&mid=2247483981&idx=1&sn=e41692db5ff0aeaeeb17af0845e2f2da&pass_ticket=VK7sGiNfNHNpo9B4Gc9SU2SnuLxOdpLg8nFQ21eSgY0%3D>)



### JDK自带的命令行工具



### JVM问题分析和调优



#### 参考

- [JVM问题分析处理手册](<https://zhuanlan.zhihu.com/p/43435903>) 