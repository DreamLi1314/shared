package org.jack.thread01;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 在写锁中获取读锁
 * @Warning: 
 * 	1. 读锁先被获取: 不会死锁, 获取写锁时如果读锁没有被释放线程会等待直到读锁被释放
 *  2. 写锁先被获取: 不会死锁, 写锁先被获得, 其他线程读锁需要等待, 当前线程读锁可以直接
 *  	获得, 当当前线程读锁释放, 写锁释放后其他线程才可获得读锁
 * @Author: Jack Li
 * @Package: thread-01 - ThreadService
 * @Date: Nov 26, 2020 10:57:49 PM
 * @Version: 1.0.0
 * @TimeComplexity: Required[O(n)] ---- Current[O(n^2)]
 * @ExecuteResult: Success!
 * @Status: Accepted
 */
public class ThreadService2 {

	public void method1() {
		try {
			
			// 保证写锁先被获取
			Thread.sleep(200);

			System.out.println("Method 1 准备获取读锁");
			lock.readLock().lock();

			System.out.println("Method 1 得到读锁");

			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();

			System.out.println("Method 1 释放读锁");

		}
	}

	public void method2() {
		try {
			System.out.println("Method 2 准备获取写锁");

			lock.writeLock().lock();

			System.out.println("Method 2 获得写锁");

			Thread.sleep(3000);

			System.out.println("Method 2 准备获取读锁");

			method3();

			System.out.println("Method 2 获得读锁并释放");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();

			System.out.println("Method 2 释放写锁");
		}
	}

	public void method3() {
		lock.readLock().lock();

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
	}

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
}
