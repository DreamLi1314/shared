package org.javafamily;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Borders
 */
public class GuiMain7 extends JFrame {

   private static GuiMain7 mainFrame;

   JPanel showBorder(Border border) {
      JPanel jPanel = new JPanel();
      jPanel.setLayout(new BorderLayout());

      String name = border.getClass().getSimpleName();

      jPanel.add(new JLabel(name, JLabel.CENTER), BorderLayout.CENTER);

      jPanel.setBorder(border);

      return jPanel;
   }

   public GuiMain7() {
      super("Borders JFrame!");
      setLayout(new GridLayout(2, 4));

      add(showBorder(new TitledBorder("Title Border")));
      add(showBorder(new EtchedBorder()));
      add(showBorder(new LineBorder(Color.BLUE)));
      add(showBorder(new MatteBorder(5, 5, 30, 30, Color.GRAY)));
      add(showBorder(new BevelBorder(BevelBorder.RAISED)));
      add(showBorder(new SoftBevelBorder(BevelBorder.LOWERED)));
      add(showBorder(new CompoundBorder(
         new EtchedBorder(), new LineBorder(Color.RED))));
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain7();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
