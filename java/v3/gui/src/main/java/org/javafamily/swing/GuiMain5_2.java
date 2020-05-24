package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * FlowLayout
 */
public class GuiMain5_2 extends JFrame {

   private static GuiMain5_2 mainFrame;

   public GuiMain5_2() {
      super("Hello JFrame!");

      JPanel jPanel=new JPanel();    //创建面板
      JButton btn1=new JButton("1");    //创建按钮
      JButton btn2=new JButton("2");
      JButton btn3=new JButton("3");
      JButton btn4=new JButton("4");
      JButton btn5=new JButton("5");
      JButton btn6=new JButton("6");
      JButton btn7=new JButton("7");
      JButton btn8=new JButton("8");
      JButton btn9=new JButton("9");
      jPanel.add(btn1);    //面板中添加按钮
      jPanel.add(btn2);
      jPanel.add(btn3);
      jPanel.add(btn4);
      jPanel.add(btn5);
      jPanel.add(btn6);
      jPanel.add(btn7);
      jPanel.add(btn8);
      jPanel.add(btn9);
      //向JPanel添加FlowLayout布局管理器，将组件间的横向和纵向间隙都设置为20像素
      jPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));
      jPanel.setBackground(Color.gray);    //设置背景色

      add(jPanel);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5_2();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
