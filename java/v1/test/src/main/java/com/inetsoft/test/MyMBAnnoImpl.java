package com.inetsoft.test;

public class MyMBAnnoImpl implements MyMB {
   private int testAttr = 100;

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
