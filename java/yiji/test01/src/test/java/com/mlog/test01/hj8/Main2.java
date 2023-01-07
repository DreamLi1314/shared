package com.mlog.test01.hj8;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main2 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int[] data = new int[11111112];

      final int n = Integer.parseInt(scanner.readLine());

      for (int i = 0; i < n; i++) {
         final String[] strings = scanner.readLine().split(" ");
         data[Integer.parseInt(strings[0])] += Integer.parseInt(strings[1]);
      }

      for (int i = 0; i < data.length; i++) {
         if(data[i] > 0) {
            System.out.println(i + " " + data[i]);
         }
      }
   }
}
