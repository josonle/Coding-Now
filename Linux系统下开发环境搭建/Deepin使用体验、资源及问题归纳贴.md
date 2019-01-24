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

