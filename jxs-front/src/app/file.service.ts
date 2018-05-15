import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {FileClass} from './FileClass';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';

@Injectable()
export class FileService {
  http : HttpClient ;
  fileUrl = 'localhost:8080/rest/api/google';
  listFiles : FileClass[] = [];
  constructor(http : HttpClient
  ) {
    this.http=http
  }
  getListFiles(){
    return this.listFiles;
  }
  addListFiles(file : FileClass){
    this.listFiles.push(file);
  }

  getFile(id: number):Observable<FileClass[]>{
    const url = `${this.fileUrl}/file/${id}`;
    return this.http.get<FileClass[]>(this.fileUrl);



  }
  tryParsing(){
      return this.http.get("https://api.github.com/users/mralexgray/repos")/*
      .map(res => {console.log("testTryparsing "+JSON.stringify(res[0].html_url))})
      */
  }
  getAllFiles(){
    const url = `${this.fileUrl}/all`;
    return this.http.get<FileClass>(this.fileUrl);
  }

}