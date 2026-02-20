import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { MatColumnIno } from "../mat-table-view/mat-column-ino";

@Component({
   selector: 'table-view-dialog',
   templateUrl: 'table-view-dialog.html',
   styleUrls: ['./table-view-dialog.scss']
})
export class TableViewDialog <T> {

   constructor(public dialogRef: MatDialogRef<TableViewDialog<T>>,
               @Inject(MAT_DIALOG_DATA) public data: any)
   {
   }

   onNoClick(): void {
      this.dialogRef.close();
   }

   get cols(): MatColumnIno[] {
      return this.data.cols;
   }
}
