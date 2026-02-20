package com.mlog.test01.hj8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main4 {
   public static void main(String[] args) throws Exception {
      final BufferedReader scanner = new BufferedReader(
         new InputStreamReader(System.in));

      final int n = Integer.parseInt(scanner.readLine());
      LinkedList<Item> list = new LinkedList<>();

      for (int i = 0; i < n; i++) {
         final String[] strings = scanner.readLine().split(" ");
         final int key = Integer.parseInt(strings[0]);
         final int value = Integer.parseInt(strings[1]);
         int j = 0;

         for (; j < list.size(); j++) {
            final int oldKey = list.get(j).key;

            if(oldKey == key) {
               list.get(j).value += value;
               break;
            }
            else if(key > oldKey && j < list.size() - 1
               && key < list.get(j + 1).key)
            {
               list.add(j + 1, new Item(key, value));

               break;
            }
            else if(key < oldKey) {
               list.add(0, new Item(key, value));
               break;
            }
         }

         if(j == list.size()) {
            list.add(new Item(key, value));
         }
      }

      StringBuilder sb = new StringBuilder();

      for (Item item : list) {
         sb.append(item.key).append(" ").append(item.value).append("\n");
      }

      System.out.println(sb);
   }

   static class Item {
      int key;
      int value;

      public Item(int key, int value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public String toString() {
         return "Item{" +
            "key=" + key +
            ", value=" + value +
            '}';
      }
   }
}
