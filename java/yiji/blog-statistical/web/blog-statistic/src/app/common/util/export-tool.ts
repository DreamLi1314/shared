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
import { ExportType } from "../enum/export-type";

export namespace ExportTool {
   export function getExportLabel(type: ExportType): string {
      switch(type) {
         case ExportType.Excel:
            return "Excel";
         case ExportType.Excel_2003:
            return "Excel 2003";
         case ExportType.PDF:
            return "PDF";
         default:
            return "";
      }
   }
}
