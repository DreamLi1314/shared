package com.mlog.test01;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Test02Tests {

   @Test
   void test() {
      System.out.println((int) '\n');
      System.out.println((int) '3');
      System.out.println((int) 'A');

      System.out.println(10 << 0);

      System.out.println((51 << 8) + 10);
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      String input = scanner.nextLine().trim();

      final int indexOf = input.lastIndexOf(" ");

      if(indexOf >= 0) {
         input = input.substring(indexOf + 1);
      }

      System.out.println(input.length());

      final String[] split = input.split(" ");

      System.out.println(split[split.length - 1].length());
   }

}
