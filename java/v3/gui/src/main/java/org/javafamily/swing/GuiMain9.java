package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Tabs
 */
public class GuiMain9 extends JFrame {

   private static GuiMain9 mainFrame;

   private String[] tabs = { "Tab1", "Tab2", "Tab3", "Tab4",
      "Tab5", "Tab6", "Tab7", "Tab8", "Tab9" };

   public GuiMain9() {
      super("JTabbedPane JFrame!");

      JTabbedPane tabbedPane = new JTabbedPane();

      // 创建一个 TextField 用于显示当前选中的 Tab
      JTextField textField = new JTextField(45);
      textField.setEnabled(false); // 设置 Enabled 为 false 禁止编辑

      // 创建一组 Tab  并加入到 tabbedPane
      for (int i = 0; i < tabs.length; i++) {
         tabbedPane.addTab(tabs[i], new JButton(tabs[i]));
      }

      add(tabbedPane);
      add(textField, BorderLayout.SOUTH);

      // 监听 Tab 切换事件, 并更新 textField 显示当前打开的 Tab.
      tabbedPane.addChangeListener(event -> {
         textField.setText(tabbedPane.getSelectedIndex() + " has been selected.");
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain9();

      SwingUtils.run(mainFrame, 400, 200);
   }
}
