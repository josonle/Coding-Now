# IDEA常用快捷键
从Eclipse变更到IDEA上开发了，快捷键这一块真的是头疼啊，重新熟悉一遍吧
## 常用
> 如果记不清了，敲Ctrl+j，然后输几个模糊的字母，有提示

- `sout`：System.out.println()
- `fori`：for循环
- `iter`：foreach循环
- `itit`：遍历Iterator
- `itli`：遍历List，反正`it`打头的是遍历xxx
- `psvm`：main方法，它这个用的是单词首字母，像静态常量方法可以敲`psf`
  - `pi`：hashCode方法
  - `ps`：toString方法
  - `psfi`：public static final int
- `ifn/inn`：判断null/非null

- `Ctrl+Alt+L`：一键格式化代码，同eclipse的`Shift+Alt+F`
  - 如果冲突了，查下其他软件（搜狗输入法、qq、网易云等）的快捷键是否占用，像我这里被网易云音乐占用了，取消就行了
- `Ctrl+Shift+Enter`：补全末尾的；或者语句的花括号{}
- `Ctrl+Alt+Enter`：在当前行上方插入新行
- `Shift+Enter`：在当前行下方插入新行
- `Ctrl+Shift+上下箭头`：将当前行代码上下移动
- `Ctrl+W`：选中一个单词
- `Ctrl+X`：剪切，没有Ctrl+D了
- 注释
  - `Ctrl+/`：//
  - `Ctrl+Shift+/`：/**/
  - `/**+Enter`：Javadoc注释，类的属性、方法，类
  - `Alt+Enter`：给方法、变量、类加上注释，不过光标要放对位置，比如放在方法名上
    - 还能更改类的访问控制符，像改成private
- `Shift+F6`：重命名
- `Ctrl+F6`：改变函数签名（访问控制符、返回类型、函数名、参数）