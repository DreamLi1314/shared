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
* @TimeComplexity: Required[O(n)] ---- Current[O(n^2)]
* @ExecuteResult: Success!
* @Status: Accepted
 */
public class ThreadService {

	public void method1() {
		
		System.out.println("Method 1 准备获取读锁");
		lock.readLock().lock();
		
		System.out.println("Method 1 得到读锁");

		try {
			Thread.sleep(2000);
			
			System.out.println("Method 1 准备获取写锁");
			
			method3();
			
			System.out.println("Method 1 获得写锁并释放");
			
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			lock.readLock().unlock();
			
			System.out.println("Method 1 释放读锁");
			
		}
	}

	public void method2() {
		System.out.println("Method 2 准备获取写锁");
		lock.writeLock().lock();
		
		System.out.println("Method 2 获得写锁");

		try {
			Thread.sleep(1000);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			lock.writeLock().unlock();
			
			System.out.println("Method 2 释放写锁");
		}
	}
	
	public void method3() {
		lock.writeLock().lock();

		try {
			Thread.sleep(500);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
}
