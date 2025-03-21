/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Tool } from "../../common/util/tool";
import { MatColumnIno } from "./mat-column-ino";

@Component({
  selector: "mat-table-view",
  templateUrl: "./mat-table-view.component.html",
  styleUrls: ["./mat-table-view.component.scss"]
})
export class MatTableView <T> implements OnInit, AfterViewInit {
   @Input() clickToSelect = true;
   @Input() hidePageSize = false;
   @Input() selectedItems: T[] = [];
   @Input() set data(dataSource: T[]) {
      this._dataSource = new MatTableDataSource(dataSource);

      if(dataSource?.length > 0) {
         this.dataSource.sort = this.sort;
         this.dataSource.paginator = this.paginator;
      }
   }

   @Input() set cols(cols: MatColumnIno[]) {
      this._cols = cols;

      this._displayedColumns = cols.map(col => col.name);
   }

   @Output() onRowSelected = new EventEmitter<T>();
   @Output() onRowUnSelected = new EventEmitter<T>();
   // @ts-ignore
   @ViewChild(MatSort, {static: true}) sort: MatSort;
   // @ts-ignore
   @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

   // @ts-ignore
   _dataSource: MatTableDataSource<T>;
   // @ts-ignore
   _cols: MatColumnIno[];
   // @ts-ignore
   _displayedColumns: string[];

   ngOnInit(): void {
   }

   ngAfterViewInit(): void {
   }

   get dataSource(): MatTableDataSource<T> {
      return this._dataSource;
   }

   get cols(): MatColumnIno[] {
      return this._cols;
   }

   get displayedColumns(): string[] {
      return this._displayedColumns;
   }

   isSelected(item: T): boolean {
      return this.selectedItems.some(it => Tool.isEquals(it, item));
   }

   cellChecked(item: T, col: MatColumnIno, value: boolean): void {
      if(col.headerCheckbox) {
         this.toggleElement(item);
      }
      else {
         // @ts-ignore
         item[col.name] = value;
      }
   }

   toggleElement(item: T): void {
      if(this.isSelected(item)) {
         this.onRowUnSelected.emit(item);
      }
      else {
         this.onRowSelected.emit(item);
      }
   }

   selectRow(row: T, event?: MouseEvent): void {
      if(!this.clickToSelect) {
         return;
      }

      if(!!event) {
         event.stopPropagation();
         event.preventDefault();
      }

      this.onRowSelected.emit(row);
   }

}
