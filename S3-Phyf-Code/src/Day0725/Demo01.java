package Day0725;

import java.util.Scanner;

public class Demo01 {
	public static void main(String[] args) {
		A a=new A("a方法的线程");
		B b=new B();
		Thread t=new Thread(b,"b方法的线程");
		
		a.start();
		t.start();
		System.out.println("main getName:"+Thread.currentThread().getName());
		System.out.println("main getId:"+Thread.currentThread().getId());
		System.out.println("main getPriority:"+Thread.currentThread().getPriority());
		System.out.println("main getState:"+Thread.currentThread().getState());

		
	}
	
	public static void a() {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入：");
		String s=sc.nextLine();
		System.out.println("您输入的是："+s);
		sc.close();
	}
	public static void b() {
		System.out.println("这是b方法");
	}
	public static class A extends Thread{
		//重写父类的构造方法
		public A(String name) {
			super(name);
		}
		public void run() {
			a();
			System.out.println("A getName:"+Thread.currentThread().getName());
			System.out.println("A getId:"+Thread.currentThread().getId());
			System.out.println("A getPriority:"+Thread.currentThread().getPriority());
			System.out.println("A getState:"+Thread.currentThread().getState());

		}
	}
	public static class B implements Runnable{
		public void run() {
			System.out.println("b()休眠10秒");
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			b();
			System.out.println("B getName:"+Thread.currentThread().getName());
			System.out.println("B getId:"+Thread.currentThread().getId());
			System.out.println("B getPriority:"+Thread.currentThread().getPriority());
			System.out.println("B getState:"+Thread.currentThread().getState());

		}
	}
	
}
