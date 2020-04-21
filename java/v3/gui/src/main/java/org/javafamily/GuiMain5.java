package org.javafamily;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;

/**
 * BorderLayout
 */
public class GuiMain5 extends JFrame {

   private static GuiMain5 mainFrame;

   public GuiMain5() {
      super("Hello JFrame!");

      add(BorderLayout.NORTH, new JButton("North"));
      add(BorderLayout.EAST, new JButton("East"));
      add(BorderLayout.CENTER, new JButton("Center"));
      add(BorderLayout.WEST, new JButton("West"));
      add(BorderLayout.SOUTH, new JButton("South"));
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
