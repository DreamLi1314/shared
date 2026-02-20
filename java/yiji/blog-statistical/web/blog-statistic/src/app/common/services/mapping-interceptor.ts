import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from "../../../environments/environment";

@Injectable()
export class MappingInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    if (environment.mappingApi) {
      const toNginxReq = req.clone({
        url: "/web2nginx/" + req.url
      });

      return next.handle(toNginxReq);
    }

    return next.handle(req);
  }
}
