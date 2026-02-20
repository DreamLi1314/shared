package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

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

      // 设置布局方式为流式布局
      setLayout(new FlowLayout());

      JTextField inputTF1 = new JTextField(30);

      // 添加一个 Input 输入框
      add(inputTF1);

      // 为 Button 添加点击的事件监听,
      // 当点击 Button 时, 将 Button 的 Class Name 写入上面添加的 input 输入框
      btn.addActionListener((e) -> inputTF1.setText(e.getSource().getClass().getSimpleName()));
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain3();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
