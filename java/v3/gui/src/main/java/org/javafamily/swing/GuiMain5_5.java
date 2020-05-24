package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * GridBagLayout
 */
public class GuiMain5_5 extends JFrame {

   private static GuiMain5_5 mainFrame;

   //向JFrame中添加JButton按钮
   public static void makeButton(String title,JFrame frame,GridBagLayout gridBagLayout,GridBagConstraints constraints)
   {
      JButton button=new JButton(title);    //创建Button对象
      gridBagLayout.setConstraints(button,constraints);
      frame.add(button);
   }

   public GuiMain5_5() {
      super("Hello JFrame!");

      GridBagLayout gbaglayout=new GridBagLayout();    //创建GridBagLayout布局管理器
      GridBagConstraints constraints=new GridBagConstraints();
      this.setLayout(gbaglayout);    //使用GridBagLayout布局管理器
      constraints.fill=GridBagConstraints.BOTH;    //组件填充显示区域
      constraints.weightx=0.0;    //恢复默认值
      constraints.gridwidth = GridBagConstraints.REMAINDER;    //结束行
      JTextField tf=new JTextField("18824341234");
      gbaglayout.setConstraints(tf, constraints);
      this.add(tf);
      constraints.weightx=0.5;    // 指定组件的分配区域
      constraints.weighty=0.2;
      constraints.gridwidth=1;
      makeButton("7",this,gbaglayout,constraints);    //调用方法，添加按钮组件
      makeButton("8",this,gbaglayout,constraints);
      constraints.gridwidth=GridBagConstraints.REMAINDER;    //结束行
      makeButton("9",this,gbaglayout,constraints);
      constraints.gridwidth=1;    //重新设置gridwidth的值

      makeButton("4",this,gbaglayout,constraints);
      makeButton("5",this,gbaglayout,constraints);
      constraints.gridwidth=GridBagConstraints.REMAINDER;
      makeButton("6",this,gbaglayout,constraints);
      constraints.gridwidth=1;

      makeButton("1",this,gbaglayout,constraints);
      makeButton("2",this,gbaglayout,constraints);
      constraints.gridwidth=GridBagConstraints.REMAINDER;
      makeButton("3",this,gbaglayout,constraints);
      constraints.gridwidth=1;

      makeButton("返回",this,gbaglayout,constraints);
      constraints.gridwidth=GridBagConstraints.REMAINDER;
      makeButton("拨号",this,gbaglayout,constraints);
      constraints.gridwidth=1;
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5_5();

      SwingUtils.run(mainFrame, 400, 300);
   }
}
