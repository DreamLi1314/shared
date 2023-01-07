import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BlogStatisticComponent } from "./blog-statistic/blog-statistic.component";

const routes: Routes = [
  {
    path: "",
    children: [
      {
        path: "blog-statistic/:timeRange",
        component: BlogStatisticComponent
      },
      {
        path: "**",
        redirectTo: "blog-statistic/1"
      }
    ]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {onSameUrlNavigation: "reload"})
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
