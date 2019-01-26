
包管理工具 `apt` 的常用命令如下：
```
sudo apt install <package-name> #安装软件包
sudo apt search <part-of-package-name> #搜索软件包
sudo apt purge <package-name> #卸载软件包 
sudo dpkg -l #显示已安装的软件包列表
sudo dpkg -l | grep <package-name> #查找指定的软件包
sudo dpkg -i <package-name>.deb #安装软件包
sudo dpkg -r <package-name> #仅卸载软件包
sudo dpkg -P <package-name> #卸载软件包并删除其配置文件
```

dpkg是Debian Package的简写，是为Debian 专门开发的套件管理系统，方便软件的安装、更新及移除。Debian的Linux发行版都使用dpkg，例如Ubuntu、Deepin

1. 下载的软件包存放位置：/var/cache/apt/archives

2. 安装后软件默认位置：/usr/share

3. 可执行文件位置：/usr/bin

4. 配置文件位置：/etc

5. lib文件位置：/usr/lib
