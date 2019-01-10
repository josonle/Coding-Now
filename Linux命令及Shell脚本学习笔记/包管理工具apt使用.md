
包管理工具 `apt` 的常用命令如下：
```
sudo apt install <package-name> #安装软件包
sudo apt search <part-of-package-name> #搜索软件包
sudo apt purge <package-name> #卸载软件包 
sudo dpkg -l #显示已安装的软件包列表
sudo dpkg -i <package-name>.deb #安装软件包
sudo dpkg -r <package-name> #仅卸载软件包
sudo dpkg -P <package-name> #卸载软件包并删除其配置文件
```