package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Dialog
 */
public class GuiMain10 extends JFrame {

   private static GuiMain10 mainFrame;

   public GuiMain10() {
      super("Dialog JFrame!");
      setLayout(new FlowLayout());

      JTextField result = new JTextField(30);
      JButton alert = new JButton("Alert");
      JButton confirm = new JButton("Confirm");
      JButton color = new JButton("Color");
      JButton input = new JButton("Input");
      JButton vals = new JButton("3 Values");

      String[] colors = {"Red", "Green"};
      String[] valaues = {"v1", "v2", "v3"};

      add(alert);
      add(confirm);
      add(color);
      add(input);
      add(vals);
      add(result);

      alert.addActionListener(e -> {
         JOptionPane.showMessageDialog(null, ((JButton) e.getSource()).getText(),
            "Error", JOptionPane.ERROR_MESSAGE);
      });

      confirm.addActionListener(e -> {
         JOptionPane.showConfirmDialog(null, ((JButton) e.getSource()).getText(),
            "Confirm", JOptionPane.YES_NO_OPTION);
      });

      color.addActionListener(e -> {
         int index = JOptionPane.showOptionDialog(null, "Choose Color", "Info",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, colors, colors[0]);

         if(index != JOptionPane.CLOSED_OPTION) {
            result.setText(colors[index]);
         }
      });

      input.addActionListener(e -> {
         String name = JOptionPane.showInputDialog(null,"Input your name!", "Info", JOptionPane.INFORMATION_MESSAGE);
         result.setText(name);
      });

      vals.addActionListener(e -> {
         String name2 = (String) JOptionPane.showInputDialog(null, "Choose one", "Info",
            JOptionPane.INFORMATION_MESSAGE, null, valaues, valaues[0]);
         result.setText(name2);
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain10();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
