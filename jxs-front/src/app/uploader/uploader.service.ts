import { Injectable } from '@angular/core';

import {
  HttpClient, HttpEvent, HttpEventType, HttpProgressEvent,
  HttpRequest, HttpResponse, HttpErrorResponse
} from '@angular/common/http';


@Injectable()
export class UploaderService {
  constructor(
     private http: HttpClient) {}

  upload(file: File) {
    if (!file) { return; }

    const req = new HttpRequest('POST', '/upload/file', file);
    return this.http.request(req)
  }

}
