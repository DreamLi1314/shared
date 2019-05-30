package com;

public class Account {
	private int amount = 1000;
	// private final ReentrantLock lock = new ReentrantLock();//锁对象

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	// public void draw(int money) {
	// lock.lock();//加锁
	// try {
	// if (amount >= money) {// 如果余额足够就取钱，假设每次取money
	// System.out.println(Thread.currentThread().getName() + "取钱" + money);
	// try {
	// Thread.sleep(1000);// 模拟银行系统卡顿的状况，或者银行职员正在数钱
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// amount -= money;// 减掉账户余额
	// System.out.println(Thread.currentThread().getName() + "取款后余额为：" +
	// amount);
	// } else {
	// System.out.println("余额不足" + amount);
	// }
	// } finally {
	// lock.unlock();//释放锁
	// }
	// }
}
