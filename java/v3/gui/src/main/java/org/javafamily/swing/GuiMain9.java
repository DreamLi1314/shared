package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;

/**
 * Buttons
 */
public class GuiMain9 extends JFrame {

   private static GuiMain9 mainFrame;

   private String[] tabs = { "Tab1", "Tab2", "Tab3", "Tab4",
      "Tab5", "Tab6", "Tab7", "Tab8", "Tab9" };

   public GuiMain9() {
      super("JTabbedPane JFrame!");

      JTabbedPane tabbedPane = new JTabbedPane();
      JTextField textField = new JTextField(45);
      textField.setEnabled(false);

      for (int i = 0; i < tabs.length; i++) {
         tabbedPane.addTab(tabs[i], new JButton(tabs[i]));
      }

      add(tabbedPane);
      add(textField, BorderLayout.SOUTH);

      tabbedPane.addChangeListener(event -> {
         textField.setText(tabbedPane.getSelectedIndex() + " has been selected.");
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain9();

      SwingConsole.run(mainFrame, 400, 200);
   }
}
