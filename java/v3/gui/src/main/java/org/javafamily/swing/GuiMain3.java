package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;

public class GuiMain3 extends JFrame {

   private static GuiMain3 mainFrame;
   private JLabel label;

   public GuiMain3() {
      super("Hello JFrame!");

      label = new JLabel("This is a label!");

      add(label);

      JButton btn = new JButton("Button");

      add(btn);

      setLayout(new FlowLayout());

      JTextField inputTF1 = new JTextField(30);

      add(inputTF1);

      btn.addActionListener((e) -> inputTF1.setText(e.getSource().getClass().getSimpleName()));
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain3();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
