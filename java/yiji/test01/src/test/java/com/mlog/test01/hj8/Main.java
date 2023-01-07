package com.mlog.test01.hj8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      final int n = Integer.parseInt(scanner.readLine());
      Map<Integer, Integer> data = new TreeMap<>();

      for (int i = 0; i < n; i++) {
         final String[] strings = scanner.readLine().split(" ");
         data.compute(Integer.parseInt(strings[0]), (key, oldValue) -> {
            if(oldValue == null) {
               oldValue = 0;
            }

            return oldValue + Integer.parseInt(strings[1]);
         });
      }

      for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
         System.out.println(entry.getKey() + " " + entry.getValue());
      }
   }
}
