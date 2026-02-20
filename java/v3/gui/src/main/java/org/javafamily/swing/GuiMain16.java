package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * FileChooser(Open/Save/Color)
 */
public class GuiMain16 extends JFrame {

   private static GuiMain16 mainFrame;

   public GuiMain16() {
      super("FileChooser JFrame!");
      setLayout(new FlowLayout());

      JTextField fileName = new JTextField(35);
      JTextField dir = new JTextField(35);

      JButton open = new JButton("Open");
      JButton save = new JButton("Save");
      JButton color = new JButton("Color");
      add(fileName);
      add(dir);
      add(open);
      add(save);
      add(color);

      open.addActionListener(e -> {
         JFileChooser fileChooser = new JFileChooser();

         int result = fileChooser.showOpenDialog(GuiMain16.this);

         if(result == JFileChooser.APPROVE_OPTION) {
            // OK
            fileName.setText(fileChooser.getSelectedFile().getName());
            dir.setText(fileChooser.getCurrentDirectory().toString());
         }
         else if(result == JFileChooser.CANCEL_OPTION) {
            // Cancel
            fileName.setText("Cancel");
            dir.setText("Cancel");
         }
      });

      save.addActionListener(e -> {
         JFileChooser fileChooser = new JFileChooser();

         int result = fileChooser.showSaveDialog(GuiMain16.this);

         if(result == JFileChooser.APPROVE_OPTION) {
            // OK
            fileName.setText(fileChooser.getSelectedFile().getName());
            dir.setText(fileChooser.getCurrentDirectory().toString());
         }
         else if(result == JFileChooser.CANCEL_OPTION) {
            // Cancel
            fileName.setText("Cancel");
            dir.setText("Cancel");
         }
      });

      color.addActionListener(e -> {
         Color colorResult = JColorChooser.showDialog(GuiMain16.this, "Choose Color", null);

         color.setForeground(colorResult);
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain16();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
