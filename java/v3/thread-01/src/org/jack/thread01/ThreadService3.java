package org.jack.thread01;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 在读锁中获取写锁
 * @Warning: 死锁
 * @Author: Jack Li
 * @Package: thread-01 - ThreadService
 * @Date: Nov 26, 2020 10:57:49 PM
 * @Version: 1.0.0
 * @TimeComplexity: Required[O(1)]
 * @ExecuteResult: 死锁
 * @Status: Accepted
 */
public class ThreadService3 {

	public void method1() {

		System.out.println("Method 1 准备获取读锁");
		lock.readLock().lock();

		System.out.println("Method 1 得到读锁");

		try {

			System.out.println("Method 1 准备获取写锁");

			lock.writeLock().lock();

			lock.writeLock().unlock();

			System.out.println("Method 1 获得写锁并释放");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();

			System.out.println("Method 1 释放读锁");

		}
	}

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
}
