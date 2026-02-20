package org.javafamily.swing;

import org.javafamily.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Draw Math.Sin
 */
public class GuiMain14 extends JFrame {

   private static GuiMain14 mainFrame;

   private int scaleFactor = 200; // 一个 PI 绘制多少个点

   private int cycleCount; // 正弦波周期数
   private int pointCount; // 绘制的点的数量
   private double[] sines; // 正弦值
   private int[] yPositions; // y 点的位置

   public GuiMain14() {
      super("Draw Math.Sin!");
      SinPanel sinPanel = new SinPanel();
      JSlider cycleSlider = new JSlider(1, 30, 5);
      JSlider scaleSlider = new JSlider(1, 20, 10);

      JPanel sliderPane = new JPanel();
      sliderPane.setLayout(new GridLayout(2, 2));

      sliderPane.add(new JLabel("Cycle Count:"));
      sliderPane.add(cycleSlider);
      sliderPane.add(new JLabel("Scale Factor:"));
      sliderPane.add(scaleSlider);

      add(sinPanel);
      add(sliderPane, BorderLayout.SOUTH);

      cycleSlider.addChangeListener(e -> {
         int newCycleCount = ((JSlider) e.getSource()).getValue();
         sinPanel.updateCycles(newCycleCount);
      });

      scaleSlider.addChangeListener(e -> {
         int newScale = ((JSlider) e.getSource()).getValue();
         sinPanel.updateScale(newScale);
      });
   }

   public static void main(String[] args) {
      mainFrame = new GuiMain14();

      SwingUtils.run(mainFrame, 800, 600);
   }

   class SinPanel extends JPanel {
      public SinPanel() {
         updateCycles(5);
      }

      public void updateCycles(int newCycleCount) {
         cycleCount = newCycleCount;
         repaintPanel();
      }

      public void updateScale(int newScale) {
         scaleFactor = newScale;
         repaintPanel();
      }

      private void repaintPanel() {
         pointCount = scaleFactor * cycleCount * 2; // 一个周期等于 2 * PI

         // 计算 sin 值
         sines = new double[pointCount];

         for(int i = 0; i < pointCount; i++) {
            double radian = Math.PI / scaleFactor * i; // 角度转弧度
            sines[i] = Math.sin(radian);
         }

         repaint(); // 重绘, 这将导致 paintComponent 的调用
      }

      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // 调用父类绘制组件

         // 计算 x 步进
         int xWidth = getWidth();
         double hSetup = xWidth * 1.0 / pointCount;

         // 计算每个点的 y
         int height = getHeight();
         yPositions = new int[pointCount];

         for(int i = 0; i < pointCount; i++) {
            yPositions[i] = (int) (sines[i] * height / 2 + height / 2);
         }

         g.setColor(Color.RED);

         // 绘制曲线 (直线逼近曲线)
         int x1, x2, y1, y2;
         for(int i = 1; i < pointCount; i++) { // 0 在原点
            x1 = (int) ((i - 1) * hSetup);
            x2 = (int) (i * hSetup);
            y1 = yPositions[i - 1];
            y2 = yPositions[i];

            g.drawLine(x1, y1, x2, y2);
         }
      }
   }
}
