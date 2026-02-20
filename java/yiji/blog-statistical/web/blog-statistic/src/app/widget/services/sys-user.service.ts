import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { SystemUser } from "../../common/data/system-user";
import { BaseSubscription } from "../base/BaseSubscription";

@Injectable({
  providedIn: "root"
})
export class SysUserService extends BaseSubscription {
  users: SystemUser[] = [];

  constructor(private http: HttpClient) {
    super();

    this.http.get("/blog-api/user/list")
       .subscribe(users =>
       {
         this.users = <SystemUser[]> users;
       })
  }

  getAllNickNames(): string[] {
    return this.users?.map(user => user.nickname);
  }
}
