package com.mlog.test01.hj6;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main4 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int num = Integer.parseInt(scanner.readLine());

//      int k = (int) Math.sqrt(num);

      StringBuilder sb = new StringBuilder();

      for (int i = 2; i <= Math.sqrt(num); i++) {
         if (num % i == 0) {
            sb.append(i).append(" ");
            num = num / i;
            i--;
         }
      }

      sb.append(num).append(" ");
      System.out.println(sb.toString());
   }

}
