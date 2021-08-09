package com.inetsoft.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

@SpringBootTest
public class TestApplicationTests {

   @Test
   public void contextLoads() throws IOException {
      ClassPathResource resource = new ClassPathResource("data.txt");
      List<Rectangle> list  = new ArrayList<>();

      try(final InputStream in = resource.getInputStream();
          final BufferedInputStream bis = new BufferedInputStream(in);
          final InputStreamReader isr = new InputStreamReader(bis);
          final BufferedReader br = new BufferedReader(isr))
      {
         final String[] lines = br.lines().toArray(String[]::new);

         for (int j = 0; j < lines.length; j++) {
            String s = lines[j];

            List<Integer> data = new ArrayList<>();
            int start = 0;

            for (int i = 0; i < s.length(); i++) {
               if(s.charAt(i) == '=') {
                  start = i;
               }

               if(s.charAt(i) == ',' || i == s.length() - 1) {
                  final int dat = Integer.parseInt(s.substring(start + 1, i));
                  data.add(dat);
               }
            }

            list.add(new Rectangle(data.get(0), data.get(1), data.get(2), data.get(3)));
         }
      }

      System.out.println(list);

      Rectangle[] rectangles = new Rectangle[list.size()];
      list.toArray(rectangles);
      Arrays.sort(rectangles, new AxisComparator2());

      System.out.println(rectangles.length);
   }

   private static class AxisComparator2 implements Comparator<Rectangle> {
      @Override
      public int compare(Rectangle ra, Rectangle rb) {
         // handle null
         if(ra == rb) {
            return 0;
         }
         if(ra == null) {
            return -1;
         }
         if(rb == null) {
            return 1;
         }

         if(((ra.x + ra.width) <= (rb.x + 1)))
         {
            return Integer.compare(ra.x, rb.x);
         }
         else if (((rb.x + rb.width) <= (ra.x + 1))) {
            return Integer.compare(ra.x, rb.x);
         }
         else {
            return Integer.compare(ra.y, rb.y);
         }
      }
   }
}
