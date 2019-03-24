### 下载源码包

因为通过apt install方式安装的git版本不算很新，所以选择编译安装，也方便指定位置
到这里[git各个发行版本](https://github.com/git/git/tags)下载最新tar.gz源码包

### 编译安装
1. 解压
`tar -zxvf git-2.21.0.tar.gz -C ~/tools` 我这里是指定解压到用户目录下的tools目录下
2. 编译安装
    2.1 配置（configure）
    ```
    make configure
    # 配置git安装位置，方便以后卸载（直接把/opt/git删除即可）
    ./configure prefix=/opt/git
    ```
    2.2 编译（make）
    ```
    sudo make
    ```
    如果编译时候报错，如/bin/sh: autoconf: command not found，提示缺少什么就安装什么，如sudo apt install autoconf	
    2.3 安装（make install）
    ```
    sudo make install
    ```
3. 在bash中添加git命令
```
vi /etc/profile
# 追加git的环境
export PATH=$PATH:/opt/git/bin
```

4. 使用
```
[josonlee@josonlee-PC:~]$ git --version 
git version 2.21.0
```
git使用可以看目录README.md中所列举几篇文章

5. 问题
我这里碰到了些问题，git在push或pull时，无法通过https方式，会被拒绝。
解决办法是直接通过ssh方式————在添加仓库时，比如`git@github.com:josonle/Coding-Now.git`，可在仓库的`.git/config`中看到
`git remote set-url origin git@github.com:josonle/Coding-Now.git`

### 配置git
`git-2.21.0/contrib/completion/`目录下有以下脚本

- 自动tab键补齐命令 git-completion.bash 自动补全
- 显示当前分支状态 git-prompt.sh 高亮显示当前分支名称

将git-completion.bash复制到用户目录`cp git-completion.bash ~/.git-completion.bash`，git-prompt.sh文件同理

```
source ~/.git-completion.bash

GIT_PS1_SHOWDIRTYSTATE=true
GIT_PS1_SHOWCOLORHINTS=true           
if [ -f ~/.git-completion.bash ]; then
  source ~/.git-prompt.sh
  PROMPT_COMMAND='__git_ps1 "[\u@\h:\w]" "\\\$ "'
fi
```