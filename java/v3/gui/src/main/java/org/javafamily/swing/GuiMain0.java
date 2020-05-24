package org.javafamily.swing;

import javax.swing.*;

public class GuiMain0 extends JFrame {

   private static GuiMain0 mainFrame;
   private JLabel label;

   public GuiMain0() {
      super("Hello JFrame!");

      label = new JLabel("This is a label!");

      add(label);

      // 设置当 Window 窗口关闭时退出 Java 主进程
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(400, 300);
      setVisible(true);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain0();
   }
}
