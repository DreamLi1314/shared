package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * MenuBar
 */
public class GuiMain11 extends JFrame {

   private static GuiMain11 mainFrame;

   public GuiMain11() {
      super("MenuBar JFrame!");
      setLayout(new FlowLayout());

      JTextField text = new JTextField(30);
      add(text);

      JComboBox<String> type = new JComboBox<>(new String[] {"t1", "t2", "t3"});

      type.addActionListener(e -> {
         SwingUtilities.invokeLater(() -> {
            int result = JOptionPane.showOptionDialog(this, "Choose one",
               "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
               new String[] {"One", "Two", "Three"}, "One");

            text.setText(result + "");
         });
      });

      add(type);

      ActionListener listener = (e) -> {
        text.setText(((JMenuItem) e.getSource()).getText());
      };

      JMenu file = new JMenu("File");
      JMenuItem newItem = new JMenuItem("New");
      newItem.addActionListener(listener);
      JMenuItem open = new JMenuItem("Open");
      open.addActionListener(listener);
      JMenuItem settings = new JMenuItem("Settings");
      settings.addActionListener(listener);

      file.add(newItem);
      file.add(open);
      file.add(settings);

      JMenu window = new JMenu("Window");
      JMenuItem restore = new JMenuItem("Restore");
      restore.addActionListener(listener);
      window.add(restore);

      JMenu help = new JMenu("Help");
      JMenuItem about = new JMenuItem("About");
      about.addActionListener(listener);
      help.add(about);

      JMenuBar jMenuBar = new JMenuBar();

      jMenuBar.add(file);
      jMenuBar.add(window);
      jMenuBar.add(help);

      setJMenuBar(jMenuBar);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain11();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
