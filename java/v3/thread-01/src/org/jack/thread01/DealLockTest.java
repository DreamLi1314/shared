package org.jack.thread01;

public class DealLockTest {
	public static void main(String[] args) {
		
//		new Thread(() -> {
//			threadService.method1();
//		}) .start();
		
//		new Thread(() -> {
//			threadService.method2();
//		}) .start();
		
		threadService.method1();
	}
	
//	private static ThreadService threadService = new ThreadService();
//	private static ThreadService2 threadService = new ThreadService2();
	private static ThreadService3 threadService = new ThreadService3();
}
