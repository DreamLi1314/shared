package com.inetsoft.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TestApplication5 {

   public static void main(String[] args) {
      List<Rectangle> list  = new ArrayList<>();




      Rectangle[] rectangles = new Rectangle[list.size()];
      list.toArray(rectangles);
      Arrays.sort(rectangles, new TestApplication5.AxisComparator2());

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
