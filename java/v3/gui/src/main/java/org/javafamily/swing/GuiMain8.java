package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * JTextPane
 */
public class GuiMain8 extends JFrame {

   private static GuiMain8 mainFrame;

   public GuiMain8() {
      super("JTextPane JFrame!");

      JButton addText = new JButton("Add Text");

      JTextPane textPane = new JTextPane();

      add(new JScrollPane(textPane));
      add(addText, BorderLayout.SOUTH);

      addText.addActionListener(event -> {
         textPane.setText("Generator String: " + UUID.randomUUID().toString());
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain8();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
