package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * PopupMenu
 */
public class GuiMain13 extends JFrame {

   private static GuiMain13 mainFrame;

   public GuiMain13() {
      super("PopupMenu JFrame!");
      setLayout(new FlowLayout());

      JTextField text = new JTextField(35);
      add(text);

      ActionListener itemListener = e -> {
         text.setText(e.getActionCommand());
      };

      JPopupMenu pop = new JPopupMenu("Popup Menu");
      JMenuItem item1 = new JMenuItem("Item1");
      item1.addActionListener(itemListener);

      JMenuItem item2 = new JMenuItem("Item2");
      item2.addActionListener(itemListener);

      JMenuItem item3 = new JMenuItem("Item3");
      item3.addActionListener(itemListener);

      pop.add(item1);
      pop.add(item2);
      pop.add(item3);

      text.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            showPopup(e);
         }

         @Override
         public void mouseReleased(MouseEvent e) {
            showPopup(e);
         }

         private void showPopup(MouseEvent e) {
            if(e.isPopupTrigger()) { // 如果是邮件 Popup 触发则响应
               pop.show(e.getComponent(), e.getX(), e.getY());
            }
         }
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain13();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
