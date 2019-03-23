- vi/vim使用 【了解些简单使用即可，现在不都是用编辑器的多嘛】
    - [Vim初学者入门指南](https://linux.cn/article-8143-1.html)
    - [Vim 快捷键速查表](https://linux.cn/article-8144-1.html)
    - [5 个针对有经验用户的 Vim 技巧](https://linux.cn/article-8148-1.html)
    - [3 个针对高级用户的 Vim 编辑器有用技巧](https://linux.cn/article-8149-1.html)
- 补充一些操作
    - 如何显示行号？按Esc后`:set nu`，取消显示`:set nu!`
    - 修改vi的配置文件，比如上面的显示行号可以设置为永久
     ```
     set number    #显示行号
     set ruler	   #右下方显示光标当前位置
     set autoindent    #设置自动缩进对齐
     set showmatch    #设置匹配模式，类似当输入一个左括号时会匹配相应的右括号 
     set ai!     #设置自动缩进
     syntax on    #设置语法高亮
     ```
     > 修改vimrc文件，可以通过`locate vimrc`查询文件在哪里，我这里是在`/etc/vim/vimrc`，直接把上面的内容添加到末尾即可
