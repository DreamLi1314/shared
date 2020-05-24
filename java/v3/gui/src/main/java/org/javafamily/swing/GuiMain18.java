package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Progress and Slider
 */
public class GuiMain18 extends JFrame {

   private static GuiMain18 mainFrame;

   public GuiMain18() {
      super("Progress and Slider!");
      setLayout(new GridLayout(2, 1));

      JProgressBar progressBar = new JProgressBar();

      ProgressMonitor monitor = new ProgressMonitor(this, "Monitor Progress",
         "Progress Note", 0, 100);
      monitor.setProgress(0);

      // 指定弹出窗口出现所需的时间。
      // 如果预测剩余时间小于此时间，则弹出窗口不会显示。
      monitor.setMillisToPopup(2000);

      JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 60);
      slider.setBorder(new TitledBorder("Slider"));
      slider.setPaintTicks(true); // 显示刻度
      slider.setMajorTickSpacing(20); // 设置主刻度的间隔
      slider.setMinorTickSpacing(5); // 设置小刻度的间隔

      add(progressBar);
      add(slider);

      // 共享 Model
      progressBar.setModel(slider.getModel());

      slider.addChangeListener(e -> {
         monitor.setProgress(((JSlider) e.getSource()).getValue());
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain18();

      SwingUtils.run(mainFrame, 800, 600);
   }
}
