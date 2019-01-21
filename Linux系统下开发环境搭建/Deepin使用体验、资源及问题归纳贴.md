- 【向Linux迁移记录】Win10下双系统Deepin15.8安装记录：https://blog.csdn.net/lzw2016/article/details/86260546
- 【向Linux迁移记录】快速入门学习Linux之常用命令：https://blog.csdn.net/lzw2016/article/details/86534753
- 【向Linux迁移记录】Java和大数据开发环境搭建：https://blog.csdn.net/lzw2016/article/details/86566873
- 【向Linux迁移记录】Deepin下Python开发环境搭建：https://blog.csdn.net/lzw2016/article/details/86567436



### Win10+Deepin 双系统安装后要不要开启 安全启动 和 快速启动

我控制变量都试过了，发现安全启动是可以再开启的，快速启动也能开启，但会遇到有时在Deepin下对Windows下的磁盘分区只有读取权限不能写入删除等。这和win10的快速启动有关系，它并没有完全关机，而是将机器启动的相关信息写入磁盘，下次再次启动时加载该部分内容以到达快速效果，但为了避免其他系统试图修改这部分内容，所以会附以只读权限



### Deepin如何在启动项及桌面添加快捷方式

`/usr/share/applications`该目录下配置

可以参考我如何配置eclipse的快捷方式：[配置eclipse启动图标并添加到桌面](https://github.com/josonle/Coding-Now/blob/master/Linux%E7%B3%BB%E7%BB%9F%E4%B8%8B%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/Deepin%E4%B8%8B%E6%90%AD%E5%BB%BAHadoop%E3%80%81Spark%E7%AD%89%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83.md#配置eclipse启动图标并添加到桌面) 