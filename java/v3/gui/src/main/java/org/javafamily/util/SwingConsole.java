package org.javafamily.util;

import javax.swing.*;
import java.awt.*;

public class SwingConsole {

   public static void run(JFrame frame, final int width, final int height) {
      SwingUtilities.invokeLater(() -> {
         frame.setTitle(frame.getClass().getSimpleName());
         frame.setSize(width, height);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);

         // Ensure the window shown on the center of screen.
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
         frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
      });
   }

}
