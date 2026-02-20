package com.mlog.test01.hj6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringJoiner;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int num = Integer.parseInt(scanner.readLine());
      int factor = 2;
      StringJoiner sj = new StringJoiner(" ");

      while (num > 1) {
         if(num % factor == 0) {
            num = num / factor;
            sj.add(factor + "");
         }
         else {
            factor = nextFactor(factor);
         }
      }

      System.out.println(sj.toString());
   }

   private static int nextFactor(int factor) {
      for (factor = factor + 1; ; factor++) {
         int j = 2;

         int end = factor / 2 + 1;

         for (; j < end; j++) {
            if(factor % j == 0) {
               break;
            }
         }

         if(j == end) {
            return factor;
         }
      }
   }
}
