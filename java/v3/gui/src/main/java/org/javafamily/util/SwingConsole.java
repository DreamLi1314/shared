package org.javafamily.util;

import javax.swing.*;

public class SwingConsole {

   public static void run(JFrame frame, final int width, final int height) {
      SwingUtilities.invokeLater(() -> {
         frame.setTitle(frame.getClass().getSimpleName());
         frame.setSize(width, height);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      });
   }

}
