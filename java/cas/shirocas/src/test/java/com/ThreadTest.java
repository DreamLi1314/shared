package com;

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
		 Account account = new Account();
		 DrawThread card = new DrawThread("张三", account, 100);
		 DrawThread paper = new DrawThread("老婆", account, 100);
		 card.start();
		 paper.start();
//		DeadLock thread = new DeadLock();
//		new Thread(thread).start();
//		thread.init();
	}
}
