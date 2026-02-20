import { HttpClient } from "@angular/common/http";
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { ActivatedRoute } from "@angular/router";
import { DateConstant } from "../common/constants/DateConstant";
import { BlogCount } from "../common/data/blog-count";
import { EchartsBarConfig } from "../common/echarts/echarts-bar-config";
import { ImageUtil } from "../common/util/image-util";
import { DownloadService } from "../download/download.service";
import { BaseSubscription } from "../widget/base/BaseSubscription";
import { EchartsChartComponent } from "../widget/chart/echarts-chart.component";
import { TableViewDialog } from "../widget/dialog/table-view-dialog";
import { BlogStatisticService } from "../widget/services/blog-statistic.service";

@Component({
  selector: 'app-blog-statistic',
  templateUrl: './blog-statistic.component.html',
  styleUrls: ['./blog-statistic.component.scss']
})
export class BlogStatisticComponent extends BaseSubscription implements OnInit {
  DateConstant = DateConstant;

  countChartModel: any;
  // @ts-ignore
  countData: BlogCount[];
  // @ts-ignore
  top1Names: string[];
  isChart = true;
  // @ts-ignore
  dataSource: MatTableDataSource<BlogCount>;

  displayedColumns: string[]
    = ["nickname", "count"];

  // @ts-ignore
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  // @ts-ignore
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild("countChart") countChart: EchartsChartComponent;

  constructor(private route: ActivatedRoute,
              private blogService: BlogStatisticService,
              public dialog: MatDialog,
              private downloadService: DownloadService,
              private snackBar: MatSnackBar,
              private http: HttpClient)
  {
    super();
  }

  ngOnInit(): void {
    this.refresh();

    this.subscriptions.add(this.blogService.onRefresh.subscribe(() => {
      this.refresh();
    }));
  }

  subDate(dateStr: string): string {
    return dateStr.split(" ")[0];
  }

  getStartTime(): string {
    return this.blogService.getStartTime();
  }

  getEndTime(): string {
    return this.blogService.getEndTime();
  }

  get total(): number {
    if(!this.countData || this.countData.length < 1) {
      return 0;
    }

    return this.countData?.map(c => c.count)
       ?.reduce((p, c) => p += c);
  }

  get unit(): string {
    return this.blogService.getUnit();
  }

  get selectTab(): number {
    return this.blogService.selectTab;
  }

  get shortUnit(): string {
    return this.blogService.getShortUnit();
  }

  get top1(): string {
    if(!!!this.top1Names) {
      return "暂无";
    }

    if(this.top1Names.length > 2) {
      return this.top1Names.slice(0, 2).join(", ");
    }

    return this.top1Names.join(", ") ;
  }

  refreshTop1Names(): void {
    if(!!!this.countData) {
      return;
    }

    const max: number = Math.max(...this.countData.map(d => d.count));

    if(max < 1) {
      return;
    }

    this.top1Names = this.countData.filter(d => d.count == max)
      .map(d => d.nickname);
  }

  openDialog(): void {
    this.http.get(`/blog-api/send-article?startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`)
       .subscribe(list =>
    {
      const dialogRef = this.dialog.open(TableViewDialog, {
        width: '900px',
        height: '500px',
        data: {
          title: "博客发文详情",
          data: list,
          cols: [
            {
              label: "姓名",
              name: "nickname",
            },
            {
              label: "文章名称",
              name: "title"
            },
            {
              label: "发文时间",
              name: "createTime"
            },
            {
              label: "更新时间",
              name: "updateTime"
            },
            {
              label: "操作",
              name: "url",
              btnElement: {
                label: "预览",
                action: (elem: any) => {
                  window.open(elem.url, "_blank");
                }
              }
            }
          ]
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          // 导出
          this.downloadService.download(
             `/blog-api/send-article?export=true&startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`);
          this.showExportMsg();
        }
      });
    });
  }

  openDialogByType(): void {
    this.http.get(`/blog-api/send-count-type?startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`)
       .subscribe(list =>
    {
      const dialogRef = this.dialog.open(TableViewDialog, {
        width: '600px',
        height: '500px',
        data: {
          title: "按类别统计博文",
          data: list,
          cols: [
            {
              label: "文章类别",
              name: "nickname",
            },
            {
              label: "文章数量",
              name: "count"
            }
          ]
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          // 导出
          this.downloadService.download(
             `/blog-api/send-count-type?export=true&startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`);
          this.showExportMsg();
        }
      });
    });
  }

  showExportMsg(): void {
    this.snackBar.open("正在导出, 请注意查收!", "关闭");
  }

  exportCount(): void {
    if(this.isChart) {
      ImageUtil.downloadFile(this.countChart.eChartsInstance.getDataURL(), "博客统计");
    }
    else {
      this.downloadService.download(
         `/blog-api/send-count?export=true&startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`);
    }

    this.showExportMsg();
  }

  private refresh(): void {
    this.http.get(`/blog-api/send-count?startTime=${this.getStartTime()}&endTime=${this.getEndTime()}`)
       .subscribe(countData =>
       {
         let data: any[][] = [];
         this.countData = <BlogCount[]> countData;
         this.dataSource = new MatTableDataSource<BlogCount>(this.countData);
         this.refreshTop1Names();

         for(let item of this.countData) {
           data.push([item.nickname, +item.count]);
         }

         this.countChartModel = EchartsBarConfig.buildBarChart(data);
       });
  }

  get countChartCols() {
    return [
      {
        label: "姓名",
        name: "nickname"
      },
      {
        label: "博客数",
        name: "count"
      }
    ];
  }

  textEnable(): boolean {
    return this.selectTab != DateConstant.HISTORY_TAB
       && this.selectTab != DateConstant.LAST_MONTH;
  }
}
