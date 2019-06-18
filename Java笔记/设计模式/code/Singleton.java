public class Singleton{
	private Singleton() {};
	//懒汉式
	private static Singleton single = null;
	//解决线程安全问题
	public static Synchronized Singleton getInstance(){
		if(single==null){
			single = new Singleton();
		}
		return single;
	}
`	//双重检查锁定
    private volatile static Singleton single1 = null;
	public static Singleton getInstance(){
		if(single1==null){
			synchronized(Singleton.class){
				if(single1==null)
					single1 = new Singleton();
			}
		}
		return single;
	}
	//静态内部类方式
	//final解决多线程问题，也减去了判断single是否实例化的性能
	public static class LazyHolder{
		private static final Singleton ISTANCE = new Singleton();
	}
	public static Singleton getInstance(){
		return LazyHolder.INSTANCE;
	}
}
