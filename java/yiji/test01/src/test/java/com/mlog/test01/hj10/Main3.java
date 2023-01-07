package com.mlog.test01.hj10;

import java.io.InputStream;

public class Main3 {
   public static void main(String[] args) throws Exception {
      final InputStream scanner = System.in;

      int[] set = new int[128];

      int tmp, count = 0;

      while ((tmp = scanner.read()) != -1 && tmp != 10) {
         if(set[tmp] == 0) {
            count++;
            set[tmp] = 1;
         }
      }

      System.out.println(count);
   }
}
