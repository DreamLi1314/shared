package com.mlog.test01.hj10;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main2 {
   public static void main(String[] args) throws Exception {
      final InputStream scanner = System.in;

      Set<Integer> set = new HashSet<>();

      int tmp;

      while ((tmp = scanner.read()) != -1 && tmp != 10) {
         set.add(tmp);
      }

      System.out.println(set.size());
   }
}
