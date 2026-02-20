package com.inetsoft.test.interfaces;

public interface Interface2 {
   default void save() {
      System.out.println("Interface2...");
   }
}
