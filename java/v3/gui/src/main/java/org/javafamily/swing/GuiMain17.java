package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;

/**
 * HTML Label
 */
public class GuiMain17 extends JFrame {

   private static GuiMain17 mainFrame;

   public GuiMain17() {
      super("HTML Label JFrame!");
      setLayout(new FlowLayout());

      // HTML 必须以 <html> 开头, 不然会当成普通文本显示
      // html 闭合标签可以不加
      JButton button = new JButton("<html><font size='12' style='color:red'>HtmlButton</font></html>");
      add(button);

      button.addActionListener(e -> {
         add(new JLabel("<html><font size='5' style='color:#00ff00'>HtmlLabel"));
         // 由于是在容器渲染完之后加入的元素, 因此需要调用 validate 去重新布局 Container
         validate();
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain17();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
