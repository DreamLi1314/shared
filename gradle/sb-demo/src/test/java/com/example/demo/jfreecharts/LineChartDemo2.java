package com.example.demo.jfreecharts;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;

public class LineChartDemo2 extends ApplicationFrame {
   public LineChartDemo2(String title) {
      super(title);
      JPanel chartPanel = createDemoPanel();
      chartPanel.setPreferredSize(new Dimension(500, 270));
      this.setContentPane(chartPanel);
   }

   private static XYDataset createDataset() {
      XYSeries series1 = new XYSeries("First");
      series1.add(1.0D, 1.0D);
      series1.add(2.0D, 4.0D);
      series1.add(3.0D, 3.0D);
      series1.add(4.0D, 5.0D);
      series1.add(5.0D, 5.0D);
      series1.add(6.0D, 7.0D);
      series1.add(7.0D, 7.0D);
      series1.add(8.0D, 8.0D);
      XYSeries series2 = new XYSeries("Second");
      series2.add(1.0D, 5.0D);
      series2.add(2.0D, 7.0D);
      series2.add(3.0D, 6.0D);
      series2.add(4.0D, 8.0D);
      series2.add(5.0D, 4.0D);
      series2.add(6.0D, 4.0D);
      series2.add(7.0D, 2.0D);
      series2.add(8.0D, 1.0D);
      XYSeries series3 = new XYSeries("Third");
      series3.add(3.0D, 4.0D);
      series3.add(4.0D, 3.0D);
      series3.add(5.0D, 2.0D);
      series3.add(6.0D, 3.0D);
      series3.add(7.0D, 6.0D);
      series3.add(8.0D, 3.0D);
      series3.add(9.0D, 4.0D);
      series3.add(10.0D, 3.0D);
      XYSeriesCollection dataset = new XYSeriesCollection();
      dataset.addSeries(series1);
      dataset.addSeries(series2);
      dataset.addSeries(series3);
      return dataset;
   }

   private static JFreeChart createChart(XYDataset dataset) {
      JFreeChart chart = ChartFactory.createXYLineChart("Line Chart Demo 2",
         "横轴", "纵轴", dataset, PlotOrientation.VERTICAL, true, true, false);
      XYPlot plot = (XYPlot) chart.getPlot();
//      plot.setDomainPannable(true);
//      plot.setRangePannable(true);
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
//      renderer.setDefaultShapesVisible(true);
//      renderer.setDefaultShapesFilled(true);
      NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//      rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

      NumberAxis xAxis2 = new NumberAxis("Domain Axis 2");
      xAxis2.setAutoRangeIncludesZero(false);
      xAxis2.setRange(50, 1000);

      plot.setDomainAxis(1, xAxis2);

      return chart;
   }

   public static JPanel createDemoPanel() {
      JFreeChart chart = createChart(createDataset());
      ChartPanel panel = new ChartPanel(chart);
      panel.setMouseWheelEnabled(true);
      return panel;
   }

   public static void main(String[] args) {
      LineChartDemo2 demo = new LineChartDemo2("JFreeChart: LineChartDemo2.java");
      demo.pack();
      UIUtils.centerFrameOnScreen(demo);
      demo.setVisible(true);
   }
}
