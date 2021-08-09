package com.inetsoft.test.interfaces;

public class InterfaceImpl1 implements Interface1, Interface2 {
   @Override
   public void save() {
      System.out.println("Save...");
   }
}
