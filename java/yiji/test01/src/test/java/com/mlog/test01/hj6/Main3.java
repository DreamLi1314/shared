package com.mlog.test01.hj6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringJoiner;

public class Main3 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int num = Integer.parseInt(scanner.readLine());

      int k = (int) Math.sqrt(num);
      StringJoiner sj = new StringJoiner(" ");

      for (long i = 2; i <= k && num > 1; ++i) {
         while (num % i == 0) {
            sj.add(i+ "");
            num /= i;
         }
      }

      if(num > 1) {
         sj.add(num + "");
      }

      System.out.println(sj.toString());
   }

}
