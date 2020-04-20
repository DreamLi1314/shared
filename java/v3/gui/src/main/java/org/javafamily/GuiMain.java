package org.javafamily;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GuiMain extends JFrame {

   private static GuiMain mainFrame;
   private JLabel label;

   public GuiMain() {
      super("Hello JFrame!");

      label = new JLabel("This is a label!");

      add(label);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(800, 600);
      setVisible(true);

      // 构造器内部或者任何 UI 组件操作内部的 sleep 调用会阻止期间所有的事件分发.
//      try {
//         TimeUnit.SECONDS.sleep(4);
//      } catch (InterruptedException e) {
//         e.printStackTrace();
//      }
   }

   public static void main(String[] args) throws Exception {
      SwingUtilities.invokeLater(() -> mainFrame = new GuiMain());

      TimeUnit.SECONDS.sleep(10);

      SwingUtilities.invokeLater(() -> mainFrame.label.setText("Changed Label"));
   }
}
