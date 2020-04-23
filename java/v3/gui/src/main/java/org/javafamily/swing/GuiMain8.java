package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Buttons
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

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain8();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
