package com.mlog.test01;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      String str;

      while((str = scanner.readLine()) != null){
         System.out.println(Integer.parseInt(str.substring(2), 16));
      }
   }
}
