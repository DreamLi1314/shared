package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * BoxLayout
 */
public class GuiMain5_6 extends JFrame {

   private static GuiMain5_6 mainFrame;

   public GuiMain5_6() {
      super("Hello JFrame!");
      Box b1=Box.createHorizontalBox();    //创建横向Box容器
      Box b2=Box.createVerticalBox();    //创建纵向Box容器
      add(b1);    //将外层横向Box添加进窗体
      b1.add(Box.createVerticalStrut(200));    //添加高度为200的垂直框架
      b1.add(new JButton("西"));    //添加按钮1
      b1.add(Box.createHorizontalStrut(140));    //添加长度为140的水平框架
      b1.add(new JButton("东"));    //添加按钮2
      b1.add(Box.createHorizontalGlue());    //添加水平胶水
      b1.add(b2);    //添加嵌套的纵向Box容器
      //添加宽度为100，高度为20的固定区域
      b2.add(Box.createRigidArea(new Dimension(100,20)));
      b2.add(new JButton("北"));    //添加按钮3
      b2.add(Box.createVerticalGlue());    //添加垂直组件
      b2.add(new JButton("南"));    //添加按钮4
      b2.add(Box.createVerticalStrut(40));    //添加长度为40的垂直框架
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5_6();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
