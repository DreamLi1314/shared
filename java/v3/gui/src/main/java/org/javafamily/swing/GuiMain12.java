package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

/**
 * MenuBar2
 */
public class GuiMain12 extends JFrame {

   private static GuiMain12 mainFrame;
   private JMenuBar jMenuBar;
   private JMenuBar jMenuBar2;
   private Random random = new Random();

   public GuiMain12() {
      super("MenuBar2 JFrame!");

      setLayout(new FlowLayout());

      JTextField text = new JTextField(30);
      text.setEditable(false);
      add(text);

      ActionListener listener = (e) -> {
         text.setText(((JMenuItem) e.getSource()).getText());
      };

      JMenu file = new JMenu("File");

      // 设置助记符, 当 Menu 打开时按下 N 直接触发 New 的事件
      JMenuItem newItem = new JMenuItem("New", KeyEvent.VK_N);
      // 当菜单打开时, 按下 Ctrl + N 触发 New 事件
      newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
      newItem.addActionListener(listener);

      JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
      open.addActionListener(listener);

      JMenuItem settings = new JMenuItem("Settings");
      settings.addActionListener(listener);

      JMenu openRecent = new JMenu("Open Recent");
      JMenuItem workspace1 = new JMenuItem("Workspace1");
      workspace1.addActionListener(listener);
      JMenuItem workspace2 = new JMenuItem("Workspace2");
      workspace2.addActionListener(listener);
      openRecent.add(workspace1);
      openRecent.add(workspace2);

      file.add(newItem);
      file.add(open);
      file.add(openRecent);
      file.add(settings);

      JMenu window = new JMenu("Window");
      ItemListener checkBoxListener = e -> {
         Object[] selectedObjects = e.getItemSelectable().getSelectedObjects();
         String command = ((JCheckBoxMenuItem) e.getItem()).getActionCommand();
         boolean state = ((JCheckBoxMenuItem) e.getItem()).getState();
         text.setText(command + "===" + state + "==="
            + selectedObjects != null ? Arrays.asList(selectedObjects).toString() : null);
      };

      JCheckBoxMenuItem restore = new JCheckBoxMenuItem("Restore");
      restore.setActionCommand("restore");
      restore.addItemListener(checkBoxListener);

      JCheckBoxMenuItem view = new JCheckBoxMenuItem("View");
      view.setActionCommand("view");
      view.addItemListener(checkBoxListener);

      window.add(restore);
      window.add(view);

      JMenu help = new JMenu("Help");
      JMenuItem about = new JMenuItem("About");
      about.addActionListener(listener);
      help.add(about);

      jMenuBar = new JMenuBar();
      jMenuBar2 = new JMenuBar();

      jMenuBar.add(file);
      jMenuBar.add(window);
      jMenuBar.add(help);

      jMenuBar2.add(new JMenu("File2"));
      jMenuBar2.add(new JMenu("Help2"));

      setJMenuBar(jMenuBar);

      JButton changeMenuBar = new JButton("Change Menu Bar");
      changeMenuBar.addActionListener(e -> {
         int value = random.nextInt(100);
         System.out.println(value);

         if(value > 50) {
            setJMenuBar(jMenuBar);
         }
         else {
            setJMenuBar(jMenuBar2);
         }

         // 刷新 Container 和子 Components
         validate();
      });

      add(changeMenuBar);
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain12();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
