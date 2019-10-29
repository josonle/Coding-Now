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

- 复制粘贴一定要学
    - 光标移到要复制的行，esc，如输入y2（复制当前和下面的两行），再移动光标到要粘贴的位置，按p即可
    - 或者esc，按v进入可视模式，移动光标选中要复制的多行，按y，然后移动光标到要粘贴的位置，按p即可
    - 今天看到一个，`esc`，`:`，然后`9,12 copy 13`指的是9到12行内容复制到第13行（copy可用move替换）
> esc下按u可以取消更改

- 删除
  - `dd`删除一行
  - `ndd`删除以当前行开始的n行
  - `dw`删除以当前字符开始的一个字符
  - `ndw`删除以当前字符开始的n个字符
  - `d$、D`删除以当前字符开始的一行字符
  - `d)`删除到下一句的开始
  - `d}`删除到下一段的开始
  - `d回车`删除2行

> 参考：[在vim中快速复制粘贴多行](https://www.cnblogs.com/MMLoveMeMM/articles/3707287.html)