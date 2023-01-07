import { EChartModel } from "../../widget/chart/model/echart-model";
import { Tool } from "../util/tool";

export class EchartsBarConfig {

  static BAR_DEFAULT_CONFIG: EChartModel = {
    initOpts: {
      "renderer":"canvas",
      "width":null,
      "height":null
    },
    options: {
      legend: {},
      tooltip: {},
      dataset: {
        source: [
        // set data
        ]
      },
      xAxis: { type: 'category' },
      yAxis: {
        splitLine:{show: false}
      },
      series: [
        {
          type: 'bar',
          itemStyle: {
            normal: {
              color: function(params: any) {
                // build a color map as your need.
                //定义一个颜色集合
                const colorList = [
                  '#5da1f8','#91c97f','#f3c96b','#de6e6a'
                ];
                //对每个bar显示一种颜色
                return colorList[params.dataIndex % colorList.length]
              }
            }
          },
          label: {
            //每个bar的最高点值显示在bar顶部
            show: true,
            position: 'top'
          }
        }
      ]
    }
  };

  public static buildBarChart(dataset: any[][]): EChartModel {
    let config = Tool.clone(EchartsBarConfig.BAR_DEFAULT_CONFIG);

    config.options["dataset"].source = dataset;

    return config;
  }

}
