package com.inetsoft.test;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TestLock {
   public static void main(String[] args) {
      double a = 0.0;

      List<Rectangle> list  = new ArrayList<>();

      final Rectangle rec1 = new Rectangle(104, 111, 71, 0);
      final Rectangle rec2 = new Rectangle(104, 103, 71, 0);
      final Rectangle rec3 = new Rectangle(104, 389, -104, -277);

      final AxisComparator2 comparator2 = new AxisComparator2();

      System.out.println(comparator2.compare(rec2, rec3));

      System.out.println(comparator2.compare(rec1, rec2));
      System.out.println(comparator2.compare(rec1, rec3));

      list.add(rec1);
      list.add(rec2);
      list.add(rec3);

      Rectangle[] rectangles = new Rectangle[list.size()];
      list.toArray(rectangles);
      Arrays.sort(rectangles, comparator2);

      System.out.println(rectangles.length);
   }

   private static class AxisComparator2 implements Comparator<Rectangle> {
      @Override
      public int compare(Rectangle ra, Rectangle rb) {
         // handle null
         if(ra == rb) {
            return 0;
         }
         else if(ra == null) {
            return -1;
         }
         else if(rb == null) {
            return 1;
         }

         if(ra.getX() + ra.getWidth() <= rb.getX() + 1 ||
            rb.getX() + rb.getWidth() <= ra.getX() + 1)
         {
            double xinc = ra.getX() - rb.getX();
            return xinc == 0 ? 0 : (xinc > 0 ? 1 : -1);
         }
         else {
            double yinc = ra.getY() - rb.getY();

            return yinc == 0 ? 0 : (yinc > 0 ? 1 : -1);
         }
      }
   }
}
