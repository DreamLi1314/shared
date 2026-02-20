package com.inetsoft.test.interfaces;

public interface Interface1 {
   default void save() {
      System.out.println("Interface1...");
   }
}
