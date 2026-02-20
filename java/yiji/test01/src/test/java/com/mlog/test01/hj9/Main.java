package com.mlog.test01.hj9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      String str = scanner.readLine();
      Set<Character> data = new HashSet<>();
      StringBuilder sb = new StringBuilder();
      char temp;

      for (int i = str.length() - 1; i >= 0; i--) {
         temp = str.charAt(i);

         if(!data.contains(temp)) {
            data.add(temp);
            sb.append(temp);
         }
      }

      System.out.println(sb.toString());
   }
}
