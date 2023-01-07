package com.example.demo.jfreecharts;

import org.jfree.chart.*;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class MultipleAxisDemo2 extends ApplicationFrame {
   public MultipleAxisDemo2(String title) {
      super(title);
      JPanel chartPanel = createDemoPanel();
      chartPanel.setPreferredSize(new Dimension(600, 270));
      this.setContentPane(chartPanel);
   }

   private static JFreeChart createChart() {
      XYDataset dataset1 = createDataset("Series 1", 100.0D, new Minute(), 200);
      JFreeChart chart = ChartFactory.createTimeSeriesChart("Multiple Axis Demo 2", "Time of Day", "Primary Range Axis", dataset1, true, true, false);
      XYPlot plot = (XYPlot) chart.getPlot();
      plot.setDomainPannable(true);
      plot.setOrientation(PlotOrientation.VERTICAL);
      NumberAxis xAxis2 = new NumberAxis("Domain Axis 2");
      xAxis2.setAutoRangeIncludesZero(false);
      plot.setDomainAxis(1, xAxis2);
      NumberAxis yAxis2 = new NumberAxis("Range Axis 2");
      plot.setRangeAxis(1, yAxis2);
      plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
      XYDataset dataset2 = createDataset("Series 2", 1000.0D, new Minute(), 170);
      plot.setDataset(1, dataset2);
      plot.mapDatasetToDomainAxis(1, 1);
      plot.mapDatasetToRangeAxis(1, 1);
      plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
      ChartUtils.applyCurrentTheme(chart);
      return chart;
   }

   private static XYDataset createDataset(String name, double base, RegularTimePeriod start, int count) {
      TimeSeries series = new TimeSeries(name);
      RegularTimePeriod period = start;
      double value = base;

      for (int i = 0; i < count; ++i) {
         series.add(period, value);
         period = period.next();
         value *= 1.0D + (Math.random() - 0.495D) / 10.0D;
      }

      TimeSeriesCollection dataset = new TimeSeriesCollection();
      dataset.addSeries(series);
      return dataset;
   }

   public static JPanel createDemoPanel() {
      JFreeChart chart = createChart();
      return new ChartPanel(chart);
   }

   public static void main(String[] args) {
      MultipleAxisDemo2 demo = new MultipleAxisDemo2("JFreeChart: MultipleAxisDemo2.java");
      demo.pack();
      UIUtils.centerFrameOnScreen(demo);
      demo.setVisible(true);
   }
}
