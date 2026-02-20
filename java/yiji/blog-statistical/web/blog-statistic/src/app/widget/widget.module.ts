import { HttpClientModule } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NgxEchartsModule } from "ngx-echarts";
import { EchartsChartComponent } from "./chart/echarts-chart.component";
import { TableViewDialog } from "./dialog/table-view-dialog";
import { MatTableView } from "./mat-table-view/mat-table-view.component";
import { ModelService } from "./services/model.service";
import { SysUserService } from "./services/sys-user.service";

@NgModule({
  exports: [
    EchartsChartComponent,
    TableViewDialog,
    MatTableView
  ],
  declarations: [
    EchartsChartComponent,
    TableViewDialog,
    MatTableView
  ],
  providers: [
    SysUserService,
    ModelService
  ],
  entryComponents: [
    TableViewDialog
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    NgxEchartsModule.forRoot({
      // @ts-ignore
      echarts: () => import("echarts")
    }),
    MatProgressSpinnerModule,
    MatButtonModule,
    MatDialogModule,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    MatCheckboxModule,
    MatIconModule,
    MatDividerModule
  ]
})
export class WidgetModule { }
