package com.inetsoft.test;

public class MyImpl implements MyMXBean  {
   private int testAttr = 100;

   public MyImpl(int testAttr) {
      this.testAttr = testAttr;
   }

   public int getTestAttr() {
      return testAttr;
   }

   public void setTestAttr(int testAttr) {
      this.testAttr = testAttr;
   }

   @Override
   public String sayHello(String name) {
      testAttr++;

      return "Hello " + name + ", " + testAttr;
   }
}
