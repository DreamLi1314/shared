package org.javafamily.util;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {

   private SwingUtils() {
   }

   /**
    * 获取 window 的中心位置
    */
   public static Point getPosition(Window window) {
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      if(window == null) {
         return new Point(dim.width  / 2, dim.height  / 2);
      }

      return new Point((dim.width - window.getWidth()) / 2,
         (dim.height - window.getHeight()) / 2);
   }

   /**
    * 在屏幕正中间显示 Frame
    * @param frame
    * @param width
    * @param height
    */
   public static void run(JFrame frame, final int width, final int height) {
      SwingUtilities.invokeLater(() -> {
         frame.setTitle(frame.getClass().getSimpleName());
         frame.setSize(width, height);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);

         // Ensure the window shown on the center of screen.
         frame.setLocation(getPosition(frame));
      });
   }

}
