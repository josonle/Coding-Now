
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

```
# Written by com.deepin.daemon.Grub2
DEEPIN_GFXMODE_DETECT=2
GRUB_CMDLINE_LINUX=""
GRUB_CMDLINE_LINUX_DEFAULT="splash quiet "
GRUB_DEFAULT=0
GRUB_DISTRIBUTOR="`/usr/bin/lsb_release -d -s 2>/dev/null || echo Deepin`"
GRUB_GFXMODE=1366x768,1360x768,1280x720,1024x768,auto
GRUB_THEME=/boot/grub/themes/deepin-fallback/theme.txt
GRUB_TIMEOUT=5
nouveau.modeset=0
```

清理apt下载的软件包缓存和不再需要的软件包、卸载软件后的残留配置文件

```
#清理旧版本的软件缓存
sudo apt-get autoclean
#清理所有软件缓存
sudo apt-get clean
#删除系统不再使用的孤立软件
sudo apt-get autoremove
#清除所有已删除包的残馀配置文件 
dpkg -l |grep ^rc|awk '{print $2}' |sudo xargs dpkg -P
```