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

import {
    HttpClient,
    HttpErrorResponse,
    HttpHeaders,
    HttpParams,
    HttpResponse
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { Tool } from "../../common/util/tool";

@Injectable({
    providedIn: "root"
})
export class ModelService {
    private readonly headers: HttpHeaders;
    private formHeaders: HttpHeaders;
    private _errorHandler: (error: any) => boolean = () => false;

    constructor(private http: HttpClient, private modalService: NgbModal) {
        this.headers = new HttpHeaders({
            "Content-Type": "application/json"
        });
        this.formHeaders = new HttpHeaders({
            "Content-Type": "application/x-www-form-urlencoded"
        });
    }

    get errorHandler(): (error: any) => boolean {
        return this._errorHandler;
    }

    set errorHandler(handler: (error: any) => boolean) {
        this._errorHandler = handler;
    }

    getModel<T>(controller: string, params?: HttpParams, handleError = true): Observable<T> {
        const options = {
            headers: this.headers,
            params: params
        };
        return this.http.get<T>(this.baseHref + controller, options).pipe(
            catchError((error) => this.handleError<T>(error, handleError))
        );
    }

    deleteModel<T>(controller: string, params?: HttpParams): Observable<T> {
        const options = {
            headers: this.headers,
            params: params
        };
        return this.http.delete<T>(this.baseHref + controller, options).pipe(
           catchError((error) => this.handleError<T>(error))
        );
    }

    // post
    sendModel<T>(controller: string, model?: any, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.post<T>(this.baseHref + controller, model, {
            headers: this.headers,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    sendModelByForm<T>(controller: string, formValue: HttpParams, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.post<T>(this.baseHref + controller, formValue.toString(), {
            headers: this.formHeaders,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    /**
     * Use put method to send model.
     */
    putModel<T>(controller: string, model: any = null, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.put<T>(this.baseHref + controller, model, {
            headers: this.headers,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    private handleError<T>(res: HttpErrorResponse, handleError = true): Observable<T> {
        let error;

        try {
            error = res.error;
        }
        catch(ignore) {
        }

        let errMsg = (!!error && !!error.message) ? error.message :
            !!res.status ? `${res.status} - ${res.statusText}` : "Server Error";

        if(!!handleError && !!errMsg && (!error || !this.errorHandler || !this.errorHandler(error))) {
            // ComponentTool.showMessageDialog(this.modalService, "Error", errMsg).then(() => {});
            alert(error);
        }

        return throwError(errMsg);
    }

    get baseHref(): string {
        return Tool.requestPrefix();
    }
}