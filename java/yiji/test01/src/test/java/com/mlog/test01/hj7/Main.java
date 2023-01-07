package com.mlog.test01.hj7;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      System.out.println(Math.round(Float.parseFloat(scanner.readLine())));
   }
}
