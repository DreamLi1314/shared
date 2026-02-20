package com.mlog.test01.hj8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class Main3 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      int[] data = new int[11111112];

      final int n = Integer.parseInt(scanner.readLine());
      Set<Integer> set = new TreeSet<>();

      for (int i = 0; i < n; i++) {
         final String[] strings = scanner.readLine().split(" ");
         final int key = Integer.parseInt(strings[0]);
         set.add(key);
         data[key] += Integer.parseInt(strings[1]);
      }

      for (Integer key : set) {
         System.out.println(key + " " + data[key]);
      }
   }
}
