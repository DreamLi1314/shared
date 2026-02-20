package com.inetsoft.test;

import javax.management.MXBean;

@MXBean
public interface MyMB {
   int getTestAttr();

   void setTestAttr(int testAttr);

   String sayHello(String name);
}
