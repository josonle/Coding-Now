以下操作都在终端进行，`Ctrl+Alt+t`打开终端，或者随便哪鼠标右键即可打开
`sudo su`可以切换root用户权限

### 安装java环境

- [官网下载](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)，我这里下的是`jdk-8u191-linux-x64.tar.gz`（我电脑64位的，你看情况选择其他版本也可以）
- 解压到 `/opt/` 目录下（其他位置也可以，opt下用来安装的），

```
tar -zxvf jdk-8u191-linux-x64.tar.gz -C /opt/
```

- 如图配置全局环境变量`sudo vi /etc/profile`，在末尾添加图中内容即可（红线内容替换成你下的版本）

![](assets/java环境配置.png)

- 使用source命令使之生效 `source /etc/profile`
- 然后输入 `java -version` 可以看到java版本信息就成功了

### eclipse安装
[点击这里下载](https://www.eclipse.org/downloads/packages/)

![](assets/eclipse下载.png)

建议选择下面的源码包直接下载下来用，省的因为网络原因导致eclipse installer无法成功安装
- 选择eclipse installer安装过程
    - `tar -zxvf eclipse-inst-linux64.tar.gz -C ～`，我这里是解压到用户目录下
    - 解压后会生成一个eclipse-installer目录，用ll命令查看，可见其下有一个可执行文件eclipse-inst
    - 命令行下运行 `eclipse-installer/eclipse-inst`,就进入图形化安装了

![](assets/eclipse安装.png)
![](assets/folder选择.png)
![](assets/安装.png)

然后，就是等待安装结束即可。我花了300多M手机流量，没按成功，网络延迟问题吧。然后，我又花了300多M手机流量直接下载源码包安装了，如下

- 直接下载package使用

![](assets/解压.png)
解压后会在用户目录下生成一个eclipse文件夹，用ll命令查看下文件夹下内容

![](assets/查看.png)

如下运行该可执行文件，然后选择下工作区间workspace就可以了
```
josonlee@josonlee-PC:~$ eclipse/eclipse 
```
![](assets/20190120220106492.png)

#### 配置eclipse启动图标并添加到桌面
看过我上一篇文章Deepin下Python开发环境配置的应该知道如何处理。在`/usr/share/applications`下新建一个eclipse.desktop文件并编辑，添加如下内容：
```
[Desktop Entry]
Version=1.0
Type=Application
Name=Eclipse For J2EE
Icon=/home/josonlee/eclipse/icon.xpm
Exec="/home/josonlee/eclipse/eclipse" %f
Comment=Java IDE for J2EE Developers
Categories=Development;IDE;
Terminal=false
```
Icon:启动项图片，Exec：可执行文件（上面提到的执行他才能启动eclipse），Comment：概述，Categories：分类，Terminal：启动时是否显示终端

#### 配置maven
1. 下载，https://mirrors.tuna.tsinghua.edu.cn/apache/maven/
2. 解压，我这里是用户目录下的tools文件夹，`tar -zxvf apache-maven-3.6.0-bin.tar.gz -C ~/tools/`
3. 配置maven的环境变量，`vi /etc/profile`，追加如下内容
```
# Maven环境配置
export MAVEN_HOME=/home/josonlee/tools/apache-maven-3.6.0
export PATH=$PATH:$MAVEN_HOME/bin:

```
4. `source /etc/profile` 使配置生效，然后输入`mvn -version`检验是否成功，如图就是OK了
![](assets/maven配置.png)

5. 在apache-maven-3.6.0/conf/setting.xml中配置阿里的镜像源

```
<mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>        
    </mirror>
```
如图处
![](assets/maven源.png)

6. 再配置下jar包下载在本地的什么位置,apache-maven-3.6.0/conf/setting.xml中配置

默认是用户目录下的`.m2`目录下
```
<localRepository>/home/josonlee/tools/Maven_Repository/</localRepository>
```

7. 命令行下执行 `mvn help:system` ,下载一些必须的包

8. 配置eclips中maven插件

因为我们这里已经安装好maven了，所有直接在eclipse中导入就好了。窗口Window->preferences–>maven–>Installations。如图选择add，然后把你maven安装的目录导进来就好了。
![](assets/add.png)

下面这个也要配置一下

![](assets/mvnsetting.png)

### MySQL数据库安装
- [deepin安装MySQL5.7](https://blog.csdn.net/sinat_37064286/article/details/82224562)
- [在deepin上安装mysql](https://blog.csdn.net/guanripeng/article/details/79626033)

![](assets/mysql安装.png)
https://www.jianshu.com/p/de6abe2245fb

### VMWare安装
首先是下载，Deepin应用商店里有这个可以直接下载。但它默认是安装在opt目录下，我根目录给的空间不够大，所以只好找它的源码包安装了。
最新的VMWare已经15版本了，版本新未必适合自己，所以就搜索了些旧版本。
- [点击这里下载最新版本](https://www.vmware.com/products/workstation-pro.html)
- [下载VMWare 14版本](http://www.anxia.com/l/vmware-workstation-12-pro)
- [下载VMWare 12](http://www.anxia.com/l/vmware-workstation-12-pro_87497)

我这里下的是14的，之前在win10上也是用的14,再分享下使用的激活码Key
```
# 14的
VF19H-8YY5L-48DQY-JEWNG-YPKF6
FF31K-AHZD1-H8ETZ-8WWEZ-WUUVA
# 15的
ZF582-0NW5N-H8D2P-0XZEE-Z22VA
```

下载下来会是类似`VMware-Workstation-Full-14.1.0-7370693.x86_64.bundle`的文件
- 移动的目标目录下（我依然是选用户目录），`mv VMware-Workstation-Full-14.1.0-7370693.x86_64.bundle ~/`
- 因为这是个安装文件，是可执行的，所有要付权限
    - `chmod 755 VMware-Workstation-Full-14.1.0-7370693.x86_64.bundle`
- 执行该文件，**执行该文件要付root权限**，否则无法安装
    - `cd ~`
    - `sudo ./VMware-Workstation-Full-14.1.0-7370693.x86_64.bundle`
- 进入界面化安装过程，剩余同Windows下一样的

![](assets/vm安装.png)

安装完成后，命令行输入vmware启动虚拟机。我这里会有弹窗提示，如图

![](assets/vm.png)

这是因为你安装的Linux内核比较新，比当前VMware编译时调用的版本高，某些内核code接口对应不上

然后，我就遇到了下图所示的问题
![](assets/vm启动失败.png)

#### Virtual machine monitor failed导致无法启动VMWare解决

查了很久，找到解决办法，进入bios里把secure boot关掉就行了

> 参考：http://stackmirror.caup.cn/page/s1dw3oz5em9d 和
https://askubuntu.com/questions/767791/vmware-virtual-machine-monitor-failed-in-ubuntu-16-04-lts

此方法不一定有用，我试了两台机子，两台都遇到了这个问题，但有一台对该方法没有用（最后是在应用商店上装的，可以用）
#### 如何卸载VMWare

上面这个方法不一定有用，没用的话，你可以选择卸载它，然后考虑安装VirtualBox
```
# 卸载vmware
sudo vmware-installer -u vmware-workstation
```
还有就是，我从deepin的启动器里看到还有vmware-Networkeditor啥的，也是用`sudo vmware-installer -u 名字 `来卸载


### VMware最小化安装centos 搭建集群
我这里选择最小化安装，没啥难度，不会网上搜一波吧。

有一点需要注意，最小化安装Centos时，网络配置处要打开网络，否则重启不能上网。如果忘选了的话，如下配置网卡也可以
```
# 激活网卡
# 切换root用户

vi /etc/sysconfig/network-scripts/ifcfg-enp0s3

# 将 ONBOOT=no 改为 ONBOOT=yes
# 重启network服务

service network restart

```

然后还需要安装net-tools包，否则ifconfig不起作用
```
yum install net-tools
```

避免网络原因导致下载速度慢，还需配置下Centos的源

0、先下载wget工具 `yum -y install wget`

---------------------
1、备份

`mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup`

2、下载新的`CentOS-Base.repo` 到`/etc/yum.repos.d/`

```
CentOS 5

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-5.repo


CentOS 6

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo


CentOS 7

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

--------------------- 
参考：[CentOS源设置 作者：Pipci](https://blog.csdn.net/Pipcie/article/details/80005006)
```

3、清理yum缓存 
`yum clean all`
4、重建缓存 
`yum makecache` 
5、升级yum
`yum -y update `

###　修改ＶＭＷａｒｅ的网络配置

这一步主要是为了给虚拟机分配一个固定ｉｐ地址，便于集群间通讯

启动器或者打开ＶＭＷａｒｅ找到ｖｍｗａｒｅ　Ｎｅｔｗｏｒｋ　Ｅｄｉｔｏｒ，如下图配置

![](assets/vm网络设置.png)
## Scala下载及安装

> Spark runs on Java 8+, Python 2.7+/3.4+ and R 3.1+. For the Scala API, Spark 2.4.0 uses Scala 2.11. You will need to use a compatible Scala version (2.11.x).
>
> Note that support for Java 7, Python 2.6 and old Hadoop versions before 2.6.5 were removed as of Spark 2.2.0. Support for Scala 2.10 was removed as of 2.3.0.

我这里是选择Spark 2.4.0，所以Scala选择2.11.12版本

下载：https://www.scala-lang.org/download/2.11.12.html

- 解压到用户目录下（你随便，我这里是应为根目录空间不太够） `tar -zxvf scala-2.11.12.tgz -C ~`
- 同java一样要配置环境变量

```
sudo vi /etc/profile

# 末尾添加
# SCALA_HOME指定scala安装在哪里
export SCALA_HOME=/home/josonlee/tools/scala-2.11.12
export PATH=$SCALA_HOME/bin:$PATH
```
如图是我scala解压的位置，以及环境变量写法
![](assets/scalalocation.png)

- scala就这样安装好了，试着命令行下输入scala写两行代码


### 启动scala报错 java.lang.NumberFormatException: For input string: "0x100" 解决

报错信息如下，貌似也不影响scala运行，因为还是可以编译代码的
```
josonlee@josonlee-PC:~$ scala
Welcome to Scala 2.11.12 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_191).
Type in expressions for evaluation. Or try :help.
[ERROR] Failed to construct terminal; falling back to unsupported
java.lang.NumberFormatException: For input string: "0x100"
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:580)
	at java.lang.Integer.valueOf(Integer.java:766)
	at jline.internal.InfoCmp.parseInfoCmp(InfoCmp.java:59)
	at jline.UnixTerminal.parseInfoCmp(UnixTerminal.java:242)
	at jline.UnixTerminal.<init>(UnixTerminal.java:65)
	at jline.UnixTerminal.<init>(UnixTerminal.java:50)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at java.lang.Class.newInstance(Class.java:442)
	at jline.TerminalFactory.getFlavor(TerminalFactory.java:211)
	at jline.TerminalFactory.create(TerminalFactory.java:102)
	at jline.TerminalFactory.get(TerminalFactory.java:186)
	at jline.TerminalFactory.get(TerminalFactory.java:192)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:243)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:235)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:223)
	at scala.tools.nsc.interpreter.jline.JLineConsoleReader.<init>(JLineReader.scala:64)
	at scala.tools.nsc.interpreter.jline.InteractiveReader.<init>(JLineReader.scala:33)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$scala$tools$nsc$interpreter$ILoop$$instantiater$1$1.apply(ILoop.scala:858)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$scala$tools$nsc$interpreter$ILoop$$instantiater$1$1.apply(ILoop.scala:855)
	at scala.tools.nsc.interpreter.ILoop.scala$tools$nsc$interpreter$ILoop$$mkReader$1(ILoop.scala:862)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$22$$anonfun$apply$10.apply(ILoop.scala:873)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$22$$anonfun$apply$10.apply(ILoop.scala:873)
	at scala.util.Try$.apply(Try.scala:192)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$22.apply(ILoop.scala:873)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$22.apply(ILoop.scala:873)
	at scala.collection.immutable.Stream.map(Stream.scala:418)
	at scala.tools.nsc.interpreter.ILoop.chooseReader(ILoop.scala:873)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1$$anonfun$newReader$1$1.apply(ILoop.scala:893)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.newReader$1(ILoop.scala:893)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.scala$tools$nsc$interpreter$ILoop$$anonfun$$preLoop$1(ILoop.scala:897)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1$$anonfun$startup$1$1.apply(ILoop.scala:964)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply$mcZ$sp(ILoop.scala:990)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:891)
	at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:891)
	at scala.reflect.internal.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:97)
	at scala.tools.nsc.interpreter.ILoop.process(ILoop.scala:891)
	at scala.tools.nsc.MainGenericRunner.runTarget$1(MainGenericRunner.scala:74)
	at scala.tools.nsc.MainGenericRunner.run$1(MainGenericRunner.scala:87)
	at scala.tools.nsc.MainGenericRunner.process(MainGenericRunner.scala:98)
	at scala.tools.nsc.MainGenericRunner$.main(MainGenericRunner.scala:103)
	at scala.tools.nsc.MainGenericRunner.main(MainGenericRunner.scala)

scala>
```

解决方法如下：
```
sudo vi /etc/profile
# 在环境变量中导入
export TERM=xterm-color
```

还有一中方法是：
> 
I found the package which causes this issue: ncurses. I downgraded ncurses to version ncurses-6.0+20170429-1 (I am using Arch Linux) and SBT starts just fine.
>
Steps for Arch Linux:
>```
cd /var/cache/pacman/pkg
sudo pacman -U ncurses-6.0+20170429-1-x86_64.pkg.tar.xz # or some other older version
```
Steps for Mac: see https://github.com/jline/jline2/issues/281
>
I think this issue was introduced with ncurses version 20170506, see: http://invisible-island.net/ncurses/NEWS.html#index-t20170506
>
+ modify tic/infocmp display of numeric values to use hexadecimal when
      they are "close" to a power of two, making the result more readable.
I filed an issue on the SBT issue tracker: https://github.com/sbt/sbt/issues/3240

参考stackoverflow上的问题：https://stackoverflow.com/questions/44317384/sbt-error-failed-to-construct-terminal-falling-back-to-unsupported

## hadoop完全分布式集群搭建
可参考我在csdn上的文章：[【向Linux迁移记录】Deepin Linux下快速Hadoop完全分布式集群搭建](https://mp.csdn.net/postedit/86618345)

三台虚拟机分别对应master、slave1、slave2

主机名	IP地址
namenode1	192.168.17.10
datanode1	192.168.17.11
datanode2	192.168.17.12

core-site.xml
```
<configuration>
 <property>
        <name>fs.defaultFS</name>
        <value>hdfs://master:9000</value>
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/hadoop/hadoop-2.6.0-cdh5.12.1/tmp</value>
 </property>
</configuration>
```
hdfs-site.xml
```
<configuration>
<property>
    <name>dfs.namenode.name.dir</name>
    <value>/home/hadoop/hadoop-2.6.0-cdh5.12.1/hdfs/name</value>
</property>
<property>
    <name>dfs.datanode.data.dir</name>
    <value>/home/hadoop/hadoop-2.6.0-cdh5.12.1/hdfs/data</value>
</property>
<property>
    <name>dfs.replication</name>
    <value>2</value>
</property>
  <property>
    <name>dfs.permissions</name>
    <value>false</value>
  </property>
</configuration>
```

yarn-site.xml
```
<property>
	<name>yarn.nodemanager.aux-services</name>
	<value>mapreduce_shuffle</value>
</property>
<property>
	<name>yarn.resourcemanager.resource-tracker.address</name>
	<value>master:8031</value>
</property>
<property>
	<name>yarn.resourcemanager.address</name>
	<value>master:8032</value>
</property>
<property>
    	<name>yarn.resourcemanager.admin.address</name>
        <value>master:8033</value>
</property>
<property>
	<name>yarn.resourcemanager.scheduler.address</name>
	<value>master:8034</value>
</property>
<property>
	<name>yarn.resourcemanager.webapp.address</name>
	<value>master:8088</value>
</property>
<property>
	<name>yarn.log-aggregation-enable</name>
	<value>true</value>
</property>
<property>
  	<name>yarn.log.server.url</name>
	<value>http://master:19888/jobhistory/logs/</value>
</property>
```

配置好后关闭虚拟机，进入文件系统将该虚拟机复制两份（当然虚拟机放在哪是你安装是定义的）
![](assets/复制vm.png)
然后在虚拟机中导入并重命名
![](assets/重命名.png)

启动虚拟机，分别修改slave1、slave2的IP地址和主机名，并重启服务（参见前文）

修改之后，重启三台虚拟机。我介意你用命令行工具来链接虚拟机,因为我是Deepin上借助vm实现centos最小化安装，不知道怎么搞定vm tools一直安装不成功。搞的虚拟机中命令界面很难看、别扭。我这里借助Deepin的命令行自带的连接服务器功能，如图
![](assets/20190123162839.png)

### ssh免密登录
1. 分别在三台虚拟机上设置秘钥，`ssh-keygen -t rsa`，然后三下回车，如图

![](assets/ssh免密.png)
默认在**/home/hadoop/.ssh**目录(用户目录下的.ssh)下生成公钥id_rsa.pub和私钥文件id_rsa

2. 将slave1、slave2节点的公钥汇总到mster的.ssh/authorized_keys文件中

```
ssh-copy-id -i ~/.ssh/id_rsa.pub master
```
然后如图操作
![](assets/传输公钥.png)

3. 把master的公钥也汇总到authorized_keys文件

```
# master节点上执行
cd .ssh/
cat id_rsa.pub >> authorized_keys
chmod 600 cat authorized_keys  //有必要赋予访问权限
```
4. 把汇总后的authorized_keys文件分别发送到slave1、slave2的.ssh目录下

```
# master节点上执行
scp authorized_keys slave1:.ssh  //执行两次
scp authorized_keys slave2:.ssh
```
如图，输入yes后，分别输入slave1、slave2的登录密码
![](assets/发送masterpub.png)

5. 测试ssh免密登录
```
ssh 主机名		//第一次连接时如有提示，输入yes，以后则没有提示也不需要密码即可连接
```
如图是slave2免密登录master、slave1，不成功的话删掉.ssh下面的文件重复上面步骤

![](assets/slave2.png)

## 启动hadoop
以下操作全在master上进行
### 格式化hdfs文件系统
```
hdfs namenode -format
```
### 启动hdfs和yarn
```
start-dfs.sh  //启动hdfs
start-yarn.sh  //启动yarn
jps  //查看启动进程
```
浏览器查看hdfs：http://192.168.17.10:50070，yarn：http://192.168.17.10:8088

注意：master下只能看到NameNode、SecondaryNameNode(未配置但依然会启动，配置的话还需要一台虚拟机)、ResourceManager；slave下有Datanode、NodeManager

```
# master上
[hadoop@master hadoop-2.6.0-cdh5.12.1]$ jps
3809 ResourceManager
3491 NameNode
3668 SecondaryNameNode
4072 Jps

# slave上
[hadoop@slave2 ~]$ jps
2102 NodeManager
2232 Jps
1998 DataNode
```
### Master节点无法启动ResourceManager解决
进入日志文件查看信息
```
cd /home/hadoop/hadoop-2.6.0-cdh5.12.1/logs/
tail -100f yarn-hadoop-resourcemanager-master.log
```

报错信息如下：
```
Caused by: java.net.BindException: Problem binding to [master::8031] java.net.BindException: 无法指定被请求的地址; For more details see:  http://wiki.apache.org/hadoop/BindException
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at org.apache.hadoop.net.NetUtils.wrapWithMessage(NetUtils.java:791)
	at org.apache.hadoop.net.NetUtils.wrapException(NetUtils.java:720)
	at org.apache.hadoop.ipc.Server.bind(Server.java:482)
	at org.apache.hadoop.ipc.Server$Listener.<init>(Server.java:688)
	at org.apache.hadoop.ipc.Server.<init>(Server.java:2376)
	at org.apache.hadoop.ipc.RPC$Server.<init>(RPC.java:1042)
	at org.apache.hadoop.ipc.ProtobufRpcEngine$Server.<init>(ProtobufRpcEngine.java:535)
	at org.apache.hadoop.ipc.ProtobufRpcEngine.getServer(ProtobufRpcEngine.java:510)
	at org.apache.hadoop.ipc.RPC$Builder.build(RPC.java:887)
	at org.apache.hadoop.yarn.factories.impl.pb.RpcServerFactoryPBImpl.createServer(RpcServerFactoryPBImpl.java:169)
	at org.apache.hadoop.yarn.factories.impl.pb.RpcServerFactoryPBImpl.getServer(RpcServerFactoryPBImpl.java:132)
	... 17 more
Caused by: java.net.BindException: 无法指定被请求的地址
	at sun.nio.ch.Net.bind0(Native Method)
	at sun.nio.ch.Net.bind(Net.java:433)
	at sun.nio.ch.Net.bind(Net.java:425)
	at sun.nio.ch.ServerSocketChannelImpl.bind(ServerSocketChannelImpl.java:223)
	at sun.nio.ch.ServerSocketAdaptor.bind(ServerSocketAdaptor.java:74)
	at org.apache.hadoop.ipc.Server.bind(Server.java:465)
	... 25 more
2019-01-23 17:26:56,226 INFO org.apache.hadoop.yarn.server.resourcemanager.ResourceManager: SHUTDOWN_MSG: 
/************************************************************
SHUTDOWN_MSG: Shutting down ResourceManager at master/192.168.17.10
************************************************************/
```

从日志可以看出是ip地址绑定出了问题`Caused by: java.net.BindException: Problem binding to [master::8031] java.net.BindException: 无法指定被请求的地址`，我这里多写了个冒号

重新编辑下yarn-site.xml，修改如下
```
<property>
        <name>yarn.resourcemanager.resource-tracker.address</name>
        <value>master:8031</value>
</property>
```
> 我上文的配置是修改后的，没有错