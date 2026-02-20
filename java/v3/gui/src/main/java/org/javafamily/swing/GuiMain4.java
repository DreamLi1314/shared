package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

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
      // 用 JScrollPane 包起来就具有了滚动条的功能.
      JScrollPane scrollPane = new JScrollPane(textArea);

      add(scrollPane);

      JButton addBtn = new JButton("Add Data");
      JButton clearBtn = new JButton("Clear Data");

      // 添加两个 Button, 一个用于向 TextArea 添加数据, 一个用于清空数据
      add(addBtn);
      add(clearBtn);

      // prepare data
      final String data = "This is data block;\n";

      // 设置 Button 监听事件
      addBtn.addActionListener((e) -> {
         textArea.append(data);
      });

      clearBtn.addActionListener((e) -> {
         textArea.setText("");
      });
   }

   public static void main(String[] args) throws Exception {
      mainFrame = new GuiMain4();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
