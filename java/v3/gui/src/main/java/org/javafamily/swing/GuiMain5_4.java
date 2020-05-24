package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * GridLayout
 */
public class GuiMain5_4 extends JFrame {

   private static GuiMain5_4 mainFrame;

   public GuiMain5_4() {
      super("Hello JFrame!");

      JPanel panel=new JPanel();    //创建面板
      //指定面板的布局为GridLayout，4行4列，间隙为5
      panel.setLayout(new GridLayout(4,4,5,5));
      panel.add(new JButton("7"));    //添加按钮
      panel.add(new JButton("8"));
      panel.add(new JButton("9"));
      panel.add(new JButton("/"));
      panel.add(new JButton("4"));
      panel.add(new JButton("5"));
      panel.add(new JButton("6"));
      panel.add(new JButton("*"));
      panel.add(new JButton("1"));
      panel.add(new JButton("2"));
      panel.add(new JButton("3"));
      panel.add(new JButton("-"));
      panel.add(new JButton("0"));
      panel.add(new JButton("."));
      panel.add(new JButton("="));
      panel.add(new JButton("+"));

      add(panel);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5_4();

      SwingUtils.run(mainFrame, 400, 300);
   }
}
