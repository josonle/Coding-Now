以下操作都在终端进行，`Ctrl+Alt+t`打开终端，或者随便哪鼠标右键即可打开
`sudo su`可以切换root用户权限

### 安装pip3和pip

python2.7和python3.6都已经安装好了，但是没有pip，执行下面指令

```
sudo apt install python3-pip  //安装pip3
sudo apt install python-pip   //安装pip（对应Python2）
```

你也可以不装pip，但有的地方还是有python2.7的需求

### 其余第三包的安装

毕竟pip都安装好了，其他的包通过 `pip install`安装即可

还有就是这里pip源在国外，下载巨慢。可以参考这个：https://bbs.deepin.org/forum.php?mod=viewthread&tid=167144
在用户目录下配置下pip源即可，我这里选的是阿里云的，也推荐中科大的速度也很快
```
mkdir ~/.pip  //创建一个.pip目录
vi ~/.pip/pip.conf  //创建并修改旗下的pip.conf文件

#写入下面内容
[global]
index-url = https://mirrors.aliyun.com/pypi/simple/

[install]
trusted-host=mirrors.aliyun.com
```
> 阿里云：http://mirrors.aliyun.com/pypi/simple
中科大：https://pypi.mirrors.ustc.edu.cn/simple/
豆瓣：http://pypi.douban.com/simple/


### pycharm安装
- 官网下载linux版本的pycharm，我这里下的是专业版（pycharm-professional-2018.3.3.tar.gz，我有edu账号）
- 解压到`/home/用户/`目录下，`tar -zxvf pycharm-professional-2018.3.3.tar.gz -C ~`
- 解压会产生一个pycharm-2018.3.3文件夹，其下bin目录中会有一个pycharm.sh脚本，`./pycharm.sh`执行即可安装
- 安装过程依据提示进行
![](assets/pycharm安装00.png)
![](assets/pycharm安装01.png)

- 如何添加桌面快捷方式和启动器快捷方式

![](assets/pycharm安装02.png)
![](assets/pycharm安装03.png)
按图片上创建桌面快捷方式可能没用

怎么说呢，我的deepin是15.8版本的，安装后启动器里就已经有pycharm的图标了。没有的话，你可以命令行下：
```
ls /usr/share/applications  //看下有没有一个jetbrains-pycharm.desktop
```
它默认给我生成的jetbrains-pycharm.desktop中内容如下：
![](assets/pycharm桌面.png)

没有的话，你可以在新建一个pycharm.desktop，然后`sudo vi pycharm.desktop`
```
[Desktop Entry]
Type=Application
Name=Pycharm
GenericName=Pycharm3
Comment=Pycharm3:The Python IDE
Exec=sh ~/pycharm-2018.3.3/bin/pycharm.sh
Icon=~/pycharm-2018.3.3/bin/pycharm.png
Terminal=pycharm
Categories=Pycharm;
```
主要是Exec、Icon改成你pycharm安装的位置，然后启动器里会有pycharm图标，你右键给发送到桌面即可

### 开发环境配置
- numpy
- pandas
- scipy
- matplotlib
- seaborn
- scikit-learn
- Pillow（3.x好像是自带的）
- jupyter notebook

以上常用，需要啥装啥吧

### Jupyter Notebook安装配置

### 注意事项
#### pip下载的包安装在哪里？
比如说下的numpy，你直接命令行下如图输入
![](assets/pip下载在哪里.png)
可以看到在这里：`Location: /home/josonlee/.local/lib/python3.6/site-packages`