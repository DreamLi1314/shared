package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * CardLayout
 */
public class GuiMain5_3 extends JFrame {

   private static GuiMain5_3 mainFrame;

   public GuiMain5_3() {
      super("Hello JFrame!");

      JPanel p0=new JPanel();    //面板0
      JPanel p1=new JPanel();    //面板1
      JPanel p2=new JPanel();    //面板2
      JPanel cards=new JPanel(new CardLayout());    //卡片式布局的面板

      JButton showP1Btn = new JButton("显示 P1");
      p0.add(showP1Btn);

      JButton showP2Btn = new JButton("显示 P2");
      p0.add(showP2Btn);

      p1.add(new JButton("登录按钮"));
      p1.add(new JButton("注册按钮"));
      p1.add(new JButton("找回密码按钮"));
      JButton showP0Btn = new JButton("显示 P0");
      p1.add(showP0Btn);
      p2.add(new JTextField("用户名文本框",20));
      p2.add(new JTextField("密码文本框",20));
      p2.add(new JTextField("验证码文本框",20));
      cards.add(p0,"p0");    //向卡片式布局面板中添加面板1
      cards.add(p1,"p1");    //向卡片式布局面板中添加面板1
      cards.add(p2,"p2");    //向卡片式布局面板中添加面板2
      CardLayout cl = (CardLayout)(cards.getLayout());

      add(cards);

      // 默认显示第一个加入的 Pane
//      cl.show(cards, "p0");

      showP0Btn.addActionListener(e -> {
         cl.show(cards, "p0");
      });

      showP1Btn.addActionListener(e -> {
         cl.show(cards, "p1");
      });

      showP2Btn.addActionListener(e -> {
         cl.show(cards, "p2");
      });

   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain5_3();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
