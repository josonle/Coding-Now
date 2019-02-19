- 【向Linux迁移记录】Win10下双系统Deepin15.8安装记录：https://blog.csdn.net/lzw2016/article/details/86260546
- 【向Linux迁移记录】快速入门学习Linux之常用命令：https://blog.csdn.net/lzw2016/article/details/86534753
- 【向Linux迁移记录】Java和大数据开发环境搭建：https://blog.csdn.net/lzw2016/article/details/86566873
- 【向Linux迁移记录】Deepin下Python开发环境搭建：https://blog.csdn.net/lzw2016/article/details/86567436
- [【向Linux迁移记录】Deepin Linux下快速Hadoop完全分布式集群搭建](https://blog.csdn.net/lzw2016/article/details/86618345)
- [【向Linux迁移记录】基于Hadoop集群的Hive安装与配置详解](https://blog.csdn.net/lzw2016/article/details/86631115)



### Win10+Deepin 双系统安装后要不要开启 安全启动 和 快速启动

我控制变量都试过了，发现安全启动是可以再开启的，快速启动也能开启，但会遇到有时在Deepin下对Windows下的磁盘分区只有读取权限不能写入删除等。这和win10的快速启动有关系，它并没有完全关机，而是将机器启动的相关信息写入磁盘，下次再次启动时加载该部分内容以到达快速效果，但为了避免其他系统试图修改这部分内容，所以会附以只读权限



### Deepin如何在启动项及桌面添加快捷方式

`/usr/share/applications`该目录下配置

可以参考我如何配置eclipse的快捷方式：[配置eclipse启动图标并添加到桌面](https://github.com/josonle/Coding-Now/blob/master/Linux%E7%B3%BB%E7%BB%9F%E4%B8%8B%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/Deepin%E4%B8%8B%E6%90%AD%E5%BB%BAHadoop%E3%80%81Spark%E7%AD%89%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83.md#配置eclipse启动图标并添加到桌面) 

> 补充一点，用户目录下的.local/share/applications 也可以配置，有些应用会自动安装启动图标在此位置

### Deepin Linux 安装启动scala报错 java.lang.NumberFormatException: For input string: "0x100" 解决

java版本是jdk1.8.0_191，scala是2.11.12版本，该错误不影响scala运行

```scala
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

解决办法：https://blog.csdn.net/lzw2016/article/details/86618570



### java使用的一个warning：Picked up _JAVA_OPTIONS: -Dawt.useSystemAAFontSettings=gasp

```
# sudo vi /etc/profile

...
unset _JAVA_OPTIONS
# java环境配置
export JAVA_HOME=/opt/jdk1.8.0_191
...

# source /etc/profile
```

建议是/etc/profile和~/.bashrc下都设置这个



### VMware tools安装问题

vmware安装成功，但是启动时显示系统内核和vmware的编译接口不匹配需要更新，但是vmware tools一直安装不成功。

如何安装：

1. 虚拟机内安装open-vm-tools：`sudo apt-get install open-vm-tools open-vm-tools-desktop open-vm-tools-dkms`
2. 参考这篇文章：[Ubuntu18.04系统下全程图解安装VMware Tools的方法](https://ywnz.com/linuxjc/3144.html)



报错解决：VMware Tools - There was a problem updating a software component. Try again later and if the problem persists, contact VMware Support

> 参考这个讨论：https://bbs.deepin.org/forum.php?mod=viewthread&tid=167661&extra



### 如何设置Deepin开机自启动脚本

参考：

- https://bbs.deepin.org/forum.php?mod=viewthread&tid=141107&page=1#pid342463
-  [deepin中实现开机脚本自启的三种方法](https://www.lolimay.cn/2018/10/14/autostart-in-deepin/)

### 如何自定义鼠标右键新建文件模板，比如说markdown文件

见：[Deepin自定义右键新建文件模版.md](https://github.com/josonle/Coding-Now/blob/master/Linux%E7%B3%BB%E7%BB%9F%E4%B8%8B%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/Deepin%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8F%B3%E9%94%AE%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E6%A8%A1%E7%89%88.md)



### Linux下如何玩游戏问题

其实我平时玩的也少，一般手游就可以对付。这里我也不是推销怎么在Linux下玩游戏，而是推荐一种Linux的打包方式——AppImage。【基于AppImage方式的游戏：[Linux 游戏站](https://www.linuxgame.cn/)】

> 下载一个应用程序，给予运行权限，双击运行！无需安装！不需要改变依赖或系统配置。使用AppImage格式分发Linux桌面应用程序，让所有常见发行版的用户运行它。 一次打包，到处运行。 覆盖所有主流桌面系统。
>
> #### 简单.
>
> AppImage的核心思想是**一个应用程序 = 一个文件** 。每个AppImage都包含应用程序以及应用程序运行所需的所有文件。换句话说，除了操作系统本身的基础组件，Appimage不需要依赖包即可运行。
>
> #### 可靠.
>
> AppImage是**上游应用打包**的理想选择，这意味着你可以直接从开发者那里获取软件，而不需要任何中间步骤，这完全符合开发者意图。非常迅速。
>
> #### 快速.
>
> AppImage应用可以直接下载并且运行，**无需安装**，并且不需要root权限。

以下网址会对你有帮助的：

- [Linux 游戏站](https://www.linuxgame.cn/)
- [AppImage中文文档](https://doc.appimage.cn/docs/home/)
- [AppImage官网](https://www.appimage.org/)
- [已有AppImage应用归纳](https://appimage.github.io/)

