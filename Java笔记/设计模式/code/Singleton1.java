public class Singleton1{
	public static final Singleton1 single = new Singleton();
	private Singleton1() {};

	public static Singleton1 getInstance(){
		return single;
	}
}
