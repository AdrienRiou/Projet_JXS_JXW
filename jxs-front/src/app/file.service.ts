import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {FileClass, FileListClass} from './FileClass';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';

@Injectable()
export class FileService {
  http : HttpClient ;
  // fileUrl = 'https://jxs-back.herokuapp.com/rest/api'
  fileUrl = 'http://localhost:8080/rest/api';
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
    const url = this.fileUrl+"/google/file/"+id;
    return this.http.get<FileClass[]>(url);

  }

  renameFile(id: number){
    const url = this.fileUrl
  }

  removeFile(id : String){
    const url = this.fileUrl+"/google/remove/" + id;
    console.log("remove : this.http.get(url) : " + url);
    return this.http.get(url);
  }

  getAllFiles(){
    const url = this.fileUrl+"/google/root";
    return this.http.get<FileListClass>(url, {withCredentials: true, headers:null});
  }

  connectUser( pseudo : string ) {
    const url = this.fileUrl+"/connect?pseudo=" + pseudo;
    return this.http.get(url, {withCredentials: true})
  }

  disconnectUser() {
    const url = this.fileUrl + "/disconnect"
    return this.http.get(url, {withCredentials: true, headers:null} );
  }

}
