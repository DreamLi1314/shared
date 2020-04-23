package org.javafamily.swing;

import org.javafamily.util.SwingConsole;

import javax.swing.*;
import java.awt.*;

/**
 * TextArea
 */
public class GuiMain4 extends JFrame {

   private static GuiMain4 mainFrame;

   public GuiMain4() {
      super("Hello JFrame!");
      setLayout(new FlowLayout());

      JTextArea textArea = new JTextArea(30, 30);
      JScrollPane scrollPane = new JScrollPane(textArea);

      add(scrollPane);

      JButton addBtn = new JButton("Add Data");
      JButton clearBtn = new JButton("Clear Data");

      add(addBtn);
      add(clearBtn);

      // prepare data
      final String data = "This is data block;\n";

      addBtn.addActionListener((e) -> {
         textArea.append(data);
      });

      clearBtn.addActionListener((e) -> {
         textArea.setText("");
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain4();

      SwingConsole.run(mainFrame, 800, 600);
   }
}
