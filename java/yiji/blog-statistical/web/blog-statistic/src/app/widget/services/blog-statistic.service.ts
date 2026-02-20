import { Injectable } from "@angular/core";
import { NavigationEnd, Router } from "@angular/router";
import { Subject } from "rxjs";
import { filter, map } from "rxjs/operators";
import { DateConstant } from "../../common/constants/DateConstant";
import { DateUtils } from "../../common/util/date-utils";
import { BaseSubscription } from "../base/BaseSubscription";

@Injectable({
  providedIn: "root"
})
export class BlogStatisticService {
  public selectTab: number = DateConstant.MONTH_TAB;
  public onRefresh: Subject<void> = new Subject<void>();

  constructor(private router: Router) {
    router.events.pipe(
      filter((event: any) => event instanceof NavigationEnd),
      map((event: NavigationEnd) => event.urlAfterRedirects)
    ).subscribe(url =>
    {
      const basePath: string = "/blog-statistic/";

      if(url.startsWith(basePath))  {
        this.selectTab = Number.parseInt(url.substr(basePath.length, 1), 10);
        this.onRefresh.next();
      }
    });
  }

  getStartTime(): string {
    const now = new Date();

    switch(this.selectTab) {
      case DateConstant.HISTORY_TAB:
        now.setFullYear(2020, 0, 1);
        return DateUtils.dateFormat(now);
      case DateConstant.LAST_MONTH:
        return DateUtils.getFirstDayOfPreviousMonth(now);
      case DateConstant.QUARTER_TAB:
        return DateUtils.getFirstDayOfQuarter(now);
      case DateConstant.YEAR_TAB:
        return DateUtils.getFirstDayOfYear(now);
      default:
        return DateUtils.getFirstDayOfMonth(now);
    }
  }

  getEndTime(): string {
    const now = new Date();

    switch(this.selectTab) {
      case DateConstant.LAST_MONTH:
        return DateUtils.getFirstDayOfMonth(now);
      default:
        return DateUtils.dateFormat(now);
    }
  }

  getUnit(): string {
    switch(this.selectTab) {
      case DateConstant.HISTORY_TAB:
        return "历史";
      case DateConstant.LAST_MONTH:
        return "上月";
      case DateConstant.QUARTER_TAB:
        return "本季";
      case DateConstant.YEAR_TAB:
        return "本年";
      default:
        return "本月";
    }
  }

  getShortUnit(): string {
    switch(this.selectTab) {
      case DateConstant.HISTORY_TAB:
        return "永恒";
      case DateConstant.LAST_MONTH:
        return "上月";
      case DateConstant.QUARTER_TAB:
        return "季";
      case DateConstant.YEAR_TAB:
        return "年";
      default:
        return "月";
    }
  }

}
