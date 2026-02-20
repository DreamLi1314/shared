package com.mlog.test01.hj6;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main2 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int num = Integer.parseInt(scanner.readLine());

      long k = (long) Math.sqrt(num);

      for (long i = 2; i <= k && num > 1; ++i) {
         while (num % i == 0) {
            System.out.print(i + " ");
            num /= i;
         }
      }

      System.out.println(num == 1 ? "" : num + " ");
   }

}
