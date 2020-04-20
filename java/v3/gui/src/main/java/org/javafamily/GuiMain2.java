package org.javafamily;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GuiMain2 extends JFrame {

   private static GuiMain2 mainFrame;
   private JLabel label;

   public GuiMain2() {
      super("Hello JFrame!");

      label = new JLabel("This is a label!");

      add(label);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain2();

      SwingConsole.run(mainFrame, 800, 600);

      TimeUnit.SECONDS.sleep(10);

      SwingUtilities.invokeLater(() -> mainFrame.label.setText("Changed Label"));
   }
}
