package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * LookAndFeel
 */
public class GuiMain19 extends JFrame {

   private static GuiMain19 mainFrame;

   public GuiMain19() {
      super("LookAndFeel JFrame!");
      setLayout(new FlowLayout());

      String[] values = "A,B,C,D,E,F".split(",");

      JButton button = new JButton("Button");
      add(new JLabel("Label"));
      add(button);
      add(new JRadioButton("Radio"));
      add(new JCheckBox("CheckBox"));
      add(new JTextField(20));
      add(new JComboBox<String>(values));
      add(new JList<String>(values));

      button.addActionListener(e -> {
         JFileChooser fileChooser = new JFileChooser();

         fileChooser.showOpenDialog(this);
      });
   }

   private static void handleError() {
      System.out.println("Please Using cross/system/motif lookAndFeel.");
   }

   public static void main(String[] args) {
      if(args == null || args.length < 1) {
         handleError();
      }
      else {
         String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();

         switch(args[0]) {
            case "cross":
               break;
            case "system":
               lookAndFeel = UIManager.getSystemLookAndFeelClassName();
               break;
            case "motif":
               lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            default:
         }

         try {
            UIManager.setLookAndFeel(lookAndFeel);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      mainFrame = new GuiMain19();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
