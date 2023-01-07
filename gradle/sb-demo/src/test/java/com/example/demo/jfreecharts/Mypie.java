package com.example.demo.jfreecharts;

import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/7/11 下午4:40
 * @description
 */
public class Mypie extends ApplicationFrame {
   // 字体设置
   private static final Font font = new Font("simsun", Font.ITALIC,22);

   public Mypie(String title) {
      super(title);
      setContentPane(new ChartPanel(getChart()));
   }

   /*
    * 封装画图所需的数据集对象DefaultPieDataset
    */
   private static DefaultPieDataset getDataset(){
      DefaultPieDataset dataset = new DefaultPieDataset();
      dataset.setValue("本科生", 50);
      dataset.setValue("研究生", 30);
      dataset.setValue("博士生", 20);
      return dataset;
   }

   /*
    * 返回JFreeChart对象
    * JFreeChart对中文支持不好， 所有涉及汉字的部分，必须重新设置字体，否则显示乱码
    */
   public JFreeChart getChart(){
      final DefaultPieDataset dataset = getDataset();
      // 有标题，无悬浮提示，无连接(true,false,false)
      JFreeChart chart = ChartFactory.createPieChart("XX公司员工学历比例图", dataset,true,false,false);

      /*
       * 重新设置字体
       */
      // 重新设置标题字体
      chart.setTitle(new TextTitle("XX公司员工学历比例图",font));
      // 重新设置图例字体
      LegendTitle legend = chart.getLegend();
      legend.setItemFont(font);
      // 重新设置统计表图像字体
      PiePlot plot = (PiePlot) chart.getPlot();
      plot.setBackgroundAlpha(0.9f);
      plot.setLabelFont(font);

      return chart;
   }

   // 测试方法
   public static void main(String[] args){
      Mypie demo = new Mypie("XX公司员工学历比例图");
      demo.pack();
      UIUtils.centerFrameOnScreen(demo);
      demo.setVisible(true);
   }

}
