## finally语句如何执行
一般是在try...catch...finally中配对使用finally，多用来释放资源。虽然这个点很简单，但还是有些地方需要注意的。
- 无论try是否发生异常，finally语句都会执行
- 如果try/catch中包含控制转移语句（return、continue、break），finally都会在这些控制语句前执行
- 但是像try/catch中有`System.exit(0)`退出JVM，或者Daemon线程退出（也就是线程被中断，被kill），finally语句都不会执行

```java
public class testFinally{
	public static int test(){
	    try{
		    Integer.parseInt("execption");
            	System.out.println("block 0");
	    }catch (Exception e){
	    	System.out.println("block 1");
		//System.exit(0);//取消的话直接退出
		return iamReturn();
	    }finally{
	    	System.out.println("block 2");
		return 2;
	    }
	}
	public static int iamReturn() {
    		System.out.println("return block");
    		return 666;
	}	
	public static void main(String[] args){
		System.out.println(test());
	}
}
```
输出：
> block 1
return block
block 2
2

```java
public class testFinally1{
	public static int test(){
	    try{
		System.out.println("block 0");
	    return 1;
	    }catch (Exception e){
	    	System.out.println("block 1");
		//return 2;
	    }finally{
	    	System.out.println("block 2");
		//return 3;
	    }
	    return 4;//小心这里，可能会出现不可达的情况，比如finally中有return，或者try/catch都有return
	}
	 	
	public static void main(String[] args){
		System.out.println(test());
	}
}
```
输出：
> block 0
block 2
1

```java
public class testFinally2{
	public static int test(){
		int i = 0;
		try{
			return i;
		}finally{
			i++;//++i也罢
		}		
	}
	public static void main(String[] args){
		System.out.println(test());
	}
}
```
输出：
> 0

这个还是输出0，不是1，即使finally会先于try种的return执行，这个涉及jvm如何编译finally语句
> Java 虚拟机会把 finally 语句块作为 subroutine（对于这个 subroutine 不知该如何翻译为好，干脆就不翻译了，免得产生歧义和误解。）直接插入到 try 语句块或者 catch 语句块的控制转移语句之前。但是，还有另外一个不可忽视的因素，那就是在执行 subroutine（也就是 finally 语句块）之前，try 或者 catch 语句块会保留其返回值到本地变量表（Local Variable Table）中。待 subroutine 执行完毕之后，再恢复保留的返回值到操作数栈中，然后通过 return 或者 throw 语句将其返回给该方法的调用者（invoker）。请注意，前文中我们曾经提到过 return、throw 和 break、continue 的区别，对于这条规则（保留返回值），只适用于 return 和 throw 语句，不适用于 break 和 continue 语句，因为它们根本就没有返回值。

这个还是挺秀的，之前都没注意过，今天看到<https://www.ibm.com/developerworks/cn/java/j-lo-finally/index.html>提到了就记下来了