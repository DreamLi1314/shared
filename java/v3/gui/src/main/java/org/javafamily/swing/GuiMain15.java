package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Dialog
 */
public class GuiMain15 extends JFrame {

   private static GuiMain15 mainFrame;

   public GuiMain15() {
      super("Dialog");
      setLayout(new FlowLayout());

      MyDialog myDialog = new MyDialog(this);

      JButton button = new JButton("Open Dialog");
      add(button);

      button.addActionListener(e -> {
         myDialog.setVisible(true);
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain15();

      SwingConsole.run(mainFrame, 800, 600);
   }

   class MyDialog extends JDialog {
      public MyDialog(Frame owner) {
         super(owner, "My Dialog Title", true);// modal = true 阻止用户输入到非 Dialog 的地方

         setLayout(new FlowLayout());
         setSize(400, 300);
         setLocation(SwingConsole.getPosition(this));

         ActionListener listener = e -> {
           JOptionPane.showMessageDialog(this, e.getActionCommand());
           dispose();
         };

         JButton ok = new JButton("OK");
         JButton cancel = new JButton("Cancel");
         ok.addActionListener(listener);
         cancel.addActionListener(listener);

         add(ok);
         add(cancel);
      }
   }
}
