import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { DateConstant } from "./common/constants/DateConstant";
import { BaseSubscription } from "./widget/base/BaseSubscription";
import { BlogStatisticService } from "./widget/services/blog-statistic.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent extends BaseSubscription implements OnInit {
  DateConstant = DateConstant;

  constructor(private blogStatisticService: BlogStatisticService) {
    super();
  }

  ngOnInit(): void {
  }

  get selectTab(): number {
    return this.blogStatisticService.selectTab;
  }

  set selectTab(selectTab: number) {
    this.blogStatisticService.selectTab = selectTab;
  }

  get currentTabClass(): string {
    return "nav-tabs font-weight-bold h6";
  }

}
