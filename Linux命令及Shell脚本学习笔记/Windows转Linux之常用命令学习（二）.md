## 2019/2/6 记录常用命令

### ps 命令
当前系统中进程的快照。它能捕获系统在某一事件的进程状态。如果你想**不断更新查看进程，可以使用top命令**

```
This version of ps accepts several kinds of options:

1   UNIX options, which may be grouped and must be preceded by a dash.
2   BSD options, which may be grouped and must not be used with a dash.
3   GNU long options, which are preceded by two dashes.
```
如上，可以通过man命令来查看ps怎么使用。ps支持三种使用的语法
> UNIX 风格，选项可以组合在一起，并且选项前必须有“-”连字符
BSD 风格，选项可以组合在一起，但是选项前不能有“-”连字符
GNU 风格的长选项，选项前有两个“-”连字符

ps命令默认在控制台显示如下四列信息：

- PID: 运行着的命令(CMD)的进程编号
- TTY: 命令所运行的位置（终端）
- TIME: 运行着的该命令所占用的CPU处理时间
- CMD: 该进程所运行的命令

查看所有进程信息，a代表all，x显示没有控制终端的进程。ax常一起使用
```
ps -ax|less
# 管道符和less命令是为了便于查看
```

根据用户查看进程信息，u表示user，我这里是查看josonlee这个用户的进程
```
ps -u josonlee|less
```

根据进程名字COMMAND或者进程号PID来查看进程信息
```
ps -f -C chrome     //查看chrome相关的进程
ps -f -p 30457      //PID是30457
```

查看某个特定进程的线程，可以使用-L 参数，后面加上特定的PID
```
ps -L 30457
# 一般也会带上-f
ps -Lf 30457
```
它会显示LWP 和 NLWP 列，前者线程ID，后者个数

查看哪个进程占用了多少cpu、内存的信息等，aux参数可显示全部信息
```
ps -aux|less
```
> %CPU: 占用的 CPU 使用率
%MEM: 占用的记忆体使用率
VSZ: 占用的虚拟内存大小
RSS: 占用的实际内存大小
STAT: 该进程的状态
START：进程启动时间
TIME：进程使用总的CPU时间

类似下面所显示的
```
josonlee@josonlee-PC:~$ ps -aux
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.0  0.0 225308  9196 ?        Ss   11:08   0:01 /sbin/init splash
root         2  0.0  0.0      0     0 ?        S    11:08   0:00 [kthreadd]
root         4  0.0  0.0      0     0 ?        I<   11:08   0:00 [kworker/0:0H]
root         6  0.0  0.0      0     0 ?        I<   11:08   0:00 [mm_percpu_wq]
root         7  0.0  0.0      0     0 ?        S    11:08   0:00 [ksoftirqd/0]
root         8  0.0  0.0      0     0 ?        I    11:08   0:01 [rcu_sched]
root         9  0.0  0.0      0     0 ?        I    11:08   0:00 [rcu_bh]
root        10  0.0  0.0      0     0 ?        S    11:08   0:00 [migration/0]
root        11  0.0  0.0      0     0 ?        S    11:08   0:00 [watchdog/0]
root        12  0.0  0.0      0     0 ?        S    11:08   0:00 [cpuhp/0]
root        13  0.0  0.0      0     0 ?        S    11:08   0:00 [cpuhp/1]
root        14  0.0  0.0      0     0 ?        S    11:08   0:00 [watchdog/1]
root        15  0.0  0.0      0     0 ?        S    11:08   0:00 [migration/1]
root        16  0.0  0.0      0     0 ?        S    11:08   0:00 [ksoftirqd/1]
root        18  0.0  0.0      0     0 ?        I<   11:08   0:00 [kworker/1:0H]
root        19  0.0  0.0      0     0 ?        S    11:08   0:00 [cpuhp/2]
root        20  0.0  0.0      0     0 ?        S    11:08   0:00 [watchdog/2]
root        21  0.0  0.0      0     0 ?        S    11:08   0:00 [migration/2]

...
```
其次是可根据cpu或内存使用大小排序
```
ps -aux --sort -pcpu|head -n 10     //根据cpu使用情况排序，并查看前十个进程情况
ps -aux --sort -pmem|head -n 10     //根据内存使用情况排序
ps -aux --sort -pmem,-pcpu|head -n 10       //根据内存和cpu使用情况排序
```
> 排序格式是：`--sort [+|-] key`，key表示你要显示的某一列的名称，+表示升序排序，而-表示降序排序。如上多列用逗号隔开


常用的参数还有`-ef`：e同a，f是格式化之意，显示进程的所有格式信息；`-eo`：o是控制输出，可以是user、pid、ppid、command、pcpu、pmem、args等
> -ef显示的列为：`UID PID  PPID  C STIME TTY TIME CMD`
> -aux显示的列为：`USER  PID %CPU %MEM VSZ  RSS TTY STAT START TIME COMMAND`

PPID是父进程的pid，C是使用CPU的资源所占百分比，STIME进程开始时间
> -ef和-aux只是两种风格

另外ps命令是静态的，可以结合watch命令实时查看进程信息，如下每1秒刷新一次查看相关信息
```
watch -n 1 'ps  -U josonlee u --sort -pmem | head -n 10'
watch -n 1 'ps  -aux  --sort -pmem | head -n 10'
```
> -U 参数按真实用户ID(RUID)筛选进程，它会从用户列表中选择真实用户名或 ID。真实用户即实际创建该进程的用户。最后的u参数用来决定以针对用户的格式输出，由`User, PID, %CPU, %MEM, VSZ, RSS, TTY, STAT, START, TIME COMMAND`这几列组成

还可以与管道符和grep来查找某些特定进程
```
ps -aux|grep chrome     //查找服务名为chrome的进程
ps -e -o pid,comm,start,etime |grep mysql       //查找mysql相关的进程pid、服务名、启动时间、持续时间
```

> 这里说下time、start、lstart、stime、etime，前面也提到过time是占用cpu时间，etime是进程持续时间，stime<start<lstart是进程启动时间，lstart最精确

参考学习自：[14 Linux ps Command Practical Examples](https://linoxide.com/how-tos/linux-ps-command-examples/)



## 2019/2/15 记录常用命令

### wc 命令

用以统计文件中字节数、字数、行数等，`wc --help`可查看

```l
wc [options] file
// 不带参数，显示行数、字数、字节数 文件名
```

选项：

-c：字节数，-m：统计字符数，-l：统计行数，-L：统计最长行的长度，-w：统计字数（空白、空格或者换行分割开为一个字）

`wc -w < file`可以不要显示文件名



### sed 命令



### fp 命令



## 2019/3/20 记录

### 查找并杀死某个进程

以网易云(netease-cloud-music)为例

kill -9 \`ps aux | grep cloud-music | grep -v grep | awk '{print $2}'\`



### 查找某段时间内修改过的文件并删除

以删除修改时间超过三天，目录/tmp下为例

`find /tmp -mtime +3 -name '*.txt' -exec rm {} \;` 

- `-name`是按文件名查找，后面如果要用正则就得用引号括起
- `-exec command {} \;`是对搜索结果做系列操作（执行一个命令），别忘记空格和分号
- `-size`是根据文件大小查找，G、M、k、c分别代表GB、MB、KB、字节
- 通过时间来查找
  - Access time 上一次文件读或者写的时间，`-mtime	-mmin`，time代表天，min代表分钟
    - `-mtime +3`修改时间超过3天，`-mtime -2`修改时间小于2天
    - `-mmin +60`修改时间超过1小时，其余类似，下面也如此
  - Modifica time 上一次文件被修改的时间，`-atime  -amin`
  - Change time 上一次文件 inode meta 信息被修改的时间，`-ctime  -cmin`
- `-user/group`通过用户/组查找
- `-mindepth/maxdepth` 限定查找深度