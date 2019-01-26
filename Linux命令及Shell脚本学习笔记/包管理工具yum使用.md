## yum命令使用
### yum的命令形式一般是如下：

```
yum [options] [command] [package ...]
```
[options]是可选的，选项包括-h（帮助），-y（当安装过程提示选择全部为"yes"），-q（不显示安装的过程）等等。[command]为所要进行的操作，[package ...]是操作的对象。

### 常用的命令包括：

```
#自动搜索最快镜像插件：
yum install yum-fastestmirror
#安装yum图形窗口插件：
yum install yumex
#查看可能批量安装的列表：
yum grouplist
```

###  安装

```
yum install  //全部安装
yum install package1 //安装指定的安装包package1
yum groupinsall group1 //安装程序组group1
```

### 更新和升级

```
yum update //全部更新
yum update package1 //更新指定程序包package1
yum check-update //检查可更新的程序
yum upgrade package1 //升级指定程序包package1
yum groupupdate group1 //升级程序组group1
```

###  查找和显示

```
yum info package1 //显示安装包信息package1
yum list //显示所有已经安装和可以安装的程序包
yum list package1 //显示指定程序包安装情况package1
yum groupinfo group1 //显示程序组group1信息
yum search string //根据关键字string查找安装包
```
###  删除程序

```
yum remove | erase package1 //删除程序包package1
yum groupremove group1 //删除程序组group1
yum deplist package1 //查看程序package1依赖情况
```

###  清除缓存

```
yum clean packages //清除缓存目录下的软件包
yum clean headers //清除缓存目录下的 headers
yum clean oldheaders //清除缓存目录下旧的 headers
yum clean, yum clean all (= yum clean packages; yum clean oldheaders) //清除缓存目录下的
```

***

**1.yum的一切配置信息都储存在一个叫yum.conf的配置文件中，通常位于/etc目 录下，这是整个yum系统的重中之重。**
 看下这个文件：

```
$ sudo more /etc/yum.conf
[main]
cachedir=/var/cache/yum
keepcache=0
debuglevel=2
logfile=/var/log/yum.log
exactarch=1
obsoletes=1
gpgcheck=1
plugins=1
metadata_expire=1800
# PUT YOUR REPOS HERE OR IN separate files named file.repo
# in /etc/yum.repos.d
```

下面简单的对这一文件作简要的说明：

> cachedir：yum缓存的目录，yum在此存储下载的rpm包和数据库，一般是/var/cache/yum。
>  debuglevel：除错级别，0──10,默认是2
>  logfile：yum的日志文件，默认是/var/log/yum.log。
>  exactarch，有两个选项1和0,代表是否只升级和你安装软件包cpu体系一致的包，如果设为1，则如你安装了一个i386的rpm，则yum不会用686的包来升级。
>  gpgchkeck= 有1和0两个选择，分别代表是否是否进行gpg校验，如果没有这一项，默认好像也是检查的。

**2.好了，接下来就是yum的使用了，首先用yum来升级软件，yum的操作大都须有超级用户的权限，当然可以用sudo。**
 `yum update`，这一步是必须的，yum会从服务器的header目录下载rpm的header，放在本地的缓存中，这可能会花费一定的时间，但比起yum 给我们带来方便，这些时间的花费又算的了什么呢？
 header下载完毕，yum会判断是否有可更新的软件包，如果有，它会询问你的意见，是否更新，选择更新，这时yum开始下载软件包并使用调用rpm安装，这可能要一定时间，取决于要更新软件的数目和网络状况，万一网络断了，也没关系，再 进行一次就可以了。
 升级完毕，以后每天只要使用`yum check-update`检查一下有无跟新，如果有，就用`yum update`进行跟新，时刻保持系统为最新，堵住一切发现的漏洞。
 用**yum update packagename** 对某个单独包进行升级。

 现在简单的把yum软件升级的一些命令罗列一下：
 (更新：我在安装wine的时候是用rpm一个一个安装的，先安装以来关系，然后在安装wine的主包，但是刚刚在论坛上发现来一个好的帖子，就yum的本地安装。参数是-localinstall
 `$yum localinstall wine-*`
 这样的话，yum会自动安装所有的依赖关系，而不用rpm一个一个的安装了，省了好多工作。还有一个与他类似的参数：
 `$yum localupdate wine-*`
 如果有wine的新版本，而且你也下载到来本地，就可以这样本地更新wine了。)

```
 1. 列出所有可更新的软件清单
命令：yum check-update
 2. 安装所有更新软件
命令：yum update
 3. 仅安装指定的软件
命令：yum install
 4. 仅更新指定的软件
命令：yum update
 5. 列出所有可安裝的软件清单
命令：yum list
```

**3. 使用yum安装和卸载软件，有个前提是yum安装的软件包都是rpm格式的。**
 安装的命令是，yum install xxx，
 yum会查询数据库，有无这一软件包，如果有软件包，则检查其依赖冲突关系。如果没有依赖冲突，那么下载安装;如果有依赖冲突，则会给出提示，询问是否要同时安装依赖，或删除冲突的包，你可以自己作出判断
 删除的命令是，`yum remove xxx`，同安装一样，yum也会查询数据库，给出解决依赖关系的提示。

```
1.用YUM安装软件包
命令：yum install
2.用YUM删除软件包
命令：yum remove
```

**4.用yum查询想安装的软件**
 我们常会碰到这样的情况，想要安装一个软件，只知道它和某方面有关，但又不能确切知道它的名字。这时yum的查询功能就起作用了。
 你可以用`yum search keyword`这样的命令来进行搜索，比如我们要则安装一个`Instant Messenger`，但又不知到底有哪些，这时不妨用 `yum search messenger`这样的指令进行搜索，yum会搜索所有可用rpm的描述，列出所有描述中和messeger有关的rpm包，于是我们可能得到 gaim，kopete等等，并从中选择。
 有时我们还会碰到安装了一个包，但又不知道其用途，我们可以用`yum info packagename`这个指令来获取信息。

```
1.使用YUM查找软件包
命令：yum search
2.列出所有可安装的软件包
命令：yum list
3.列出所有可更新的软件包
命令：yum list updates
4.列出所有已安装的软件包
命令：yum list installed
5.列出所有已安装但不在 Yum Repository 內的软件包
命令：yum list extras
6.列出所指定的软件包
命令：yum list 
7.使用YUM获取软件包信息
命令：yum info 
8.列出所有软件包的信息
命令：yum info
9.列出所有可更新的软件包信息
命令：yum info updates
10.列出所有已安裝的软件包信息
命令：yum info installed
11.列出所有已安裝但不在 Yum Repository 內的软件包信息
命令：yum info extras
12.列出软件包提供哪些文件
命令：yum provides
```

**5. 清除YUM缓存**
 yum 会把下载的软件包和header存储在cache中，而不会自动删除。
 如果我们觉得它们占用了磁盘空间，可以使用yum clean指令进行清除，更精确的用法是yum clean headers清除header，yum clean packages清除下载的rpm包，yum clean all 清除所有

```
1.清除缓存目录(/var/cache/yum)下的软件包
命令：yum clean packages
2.清除缓存目录(/var/cache/yum)下的 headers
命令：yum clean headers
3.清除缓存目录(/var/cache/yum)下旧的 headers
命令：yum clean oldheaders
4.清除缓存目录(/var/cache/yum)下的软件包及旧的headers
命令：yum clean, yum clean all (= yum clean packages; yum clean oldheaders)
```

> 引自：https://www.jianshu.com/p/0cf2c57bd61b