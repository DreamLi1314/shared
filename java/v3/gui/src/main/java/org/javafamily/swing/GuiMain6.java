package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

/**
 * Buttons
 */
public class GuiMain6 extends JFrame {

   private static GuiMain6 mainFrame;

   public GuiMain6() {
      super("Buttons JFrame!");
      setLayout(new FlowLayout());

      JButton jButton_tooltip = new JButton("JButton Tooltip");
      jButton_tooltip.setToolTipText("This is tooltip.");

      add(jButton_tooltip);

      add(new JToggleButton("JToggleButton"));

      add(new JCheckBox("JCheckBox"));

      add(new JRadioButton("JRadioButton"));

      JPanel jPanel = new JPanel();
      jPanel.setBorder(new TitledBorder("Direction"));

      jPanel.add(new BasicArrowButton(BasicArrowButton.NORTH)); // up
      jPanel.add(new BasicArrowButton(BasicArrowButton.SOUTH)); // down
      jPanel.add(new BasicArrowButton(BasicArrowButton.EAST)); // left
      jPanel.add(new BasicArrowButton(BasicArrowButton.WEST)); // right

      add(jPanel);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain6();

      SwingUtils.run(mainFrame, 350, 200);
   }
}
