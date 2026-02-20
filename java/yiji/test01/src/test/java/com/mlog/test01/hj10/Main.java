package com.mlog.test01.hj10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      String str = scanner.readLine();

      Set<Character> set = new HashSet<>();

      final char[] chars = str.toCharArray();

      for (char aChar : chars) {
         set.add(aChar);
      }

      System.out.println(set.size());
   }
}
