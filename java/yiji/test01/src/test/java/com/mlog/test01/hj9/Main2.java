package com.mlog.test01.hj9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main2 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      String str = scanner.readLine();
      char[] chars = str.toCharArray();
      char temp;

      StringBuilder sb = new StringBuilder();
      Set<Character> data = new HashSet<>();

      for (int i = chars.length - 1; i >= 0; i--) {
         temp = chars[i];

         if(!data.contains(temp)) {
            data.add(temp);
            sb.append(temp);
         }
      }

      System.out.println(sb.toString());
   }
}
