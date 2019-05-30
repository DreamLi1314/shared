package com;

public class DrawThread extends Thread {
	private Account account;// 取钱的时候操作一个账户
	private int money;// 每次取多少钱

	public DrawThread(String name, Account account, int money) {
		super(name);
		this.account = account;
		this.money = money;
	}

	public void run() {
		System.out.println(super.getName() + "进入 run");
		for (int i = 0; i < 1000; i++) {// 一直取钱，目的是让多个线程一直执行，抢占CPU资源
			System.out.println(super.getName() + "进入 循环");
			synchronized (account) {
				if (account.getAmount() >= money) {// 如果余额足够就取钱，假设每次取money
					System.out.println(super.getName() + "取钱" + money);
					try {
						Thread.sleep(500);// 模拟银行系统卡顿的状况，或者银行职员正在数钱
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					account.setAmount(account.getAmount() - money);// 减掉账户余额
					System.out.println(super.getName() + "取款后余额为：" + account.getAmount());
				} else {
					System.out.println("余额不足" + account.getAmount());
					return;
				}
			}
		}

	}

}
