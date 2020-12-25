package com.inetsoft.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
   public static void main(String[] args) {
      Lock lock1 = new ReentrantLock();
      Lock lock2 = new ReentrantLock();

      new Thread(() -> {
         try {
            for(int i = 0; i < 100; i++) {
               lock1.lock();

               System.out.println("thread1 get lock1");

               lock2.lock();
               System.out.println("thread1 get lock2");

               TimeUnit.SECONDS.sleep(1);

               lock2.unlock();

               lock1.unlock();
               System.out.println("====thread1== " + i);
            }
         } catch(InterruptedException e) {
            e.printStackTrace();
         }
      }).start();

      new Thread(() -> {
         try {
            for(int i = 0; i < 100; i++) {
               lock2.lock();

               System.out.println("thread2 get lock2");

               lock1.lock();
               System.out.println("thread2 get lock1");

               TimeUnit.SECONDS.sleep(1);

               lock1.unlock();

               lock2.unlock();

               System.out.println("====thread2== " + i);
            }
         } catch(InterruptedException e) {
            e.printStackTrace();
         }
      }).start();


   }
}
