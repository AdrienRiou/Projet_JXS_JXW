import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {FileClass, FileListClass} from './FileClass';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

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
  public fileSource = new BehaviorSubject<FileClass> ({name:"name", id:"id", lastEditDate:"lastEditDate", size:"size",
  creationDate:"creationDate", authors:[], isFolder : true, service:""  });
  public fileListDislay = new BehaviorSubject<FileClass[]> ([]);
  startDisplay : boolean = false;
  public service = new BehaviorSubject<string>("")

  currentFile = this.fileSource.asObservable();



  constructor(http : HttpClient
  ) {
    this.http=http
  }

  changeFile(file:FileClass){
    this.fileSource.next(file);
  }

  changeFileList(fileList:FileClass[]){
    this.fileListDislay.next(fileList);
  }

  changeService(service:string){
    this.service.next(service);
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

  getFileFolder(id: string){
    const url = this.fileUrl+"/"+this.service.getValue() +"/parent?id=" + id;
    return this.http.get<FileListClass>(url, {withCredentials: true, headers:null});
  }

  renameFile(id: string){
    var str = ""
    if (this.service.getValue()=="dropbox"){
      str = "id:"
    }
    else{
      str =""
    }

    const url = this.fileUrl+ "/" + this.service.getValue() + "/rename/"+str+id;
    console.log("renameFile = " + url)
    return this.http.get(url, {withCredentials: true, headers:null});
  }

  removeFile(id : String){
    const url = this.fileUrl+"/"+this.service.getValue() +"/remove?id=" + id;
    console.log("remove : this.http.get(url) : " + url);
    return this.http.get(url, {withCredentials: true, headers:null});
  }

  getAllFilesGoogle(){
    const url = this.fileUrl+"/google/root";
    return this.http.get<FileListClass>(url, {withCredentials: true, headers:null});
  }

  getAllFilesDropbox(){
    const url = this.fileUrl+"/dropbox/root";
    return this.http.get<FileListClass>(url, {withCredentials: true, headers:null});
  }

  connectUser( pseudo : string ) {
    console.log("ConnectUser");
    console.log(this.fileUrl)
    const url = this.fileUrl+"/connect?pseudo=" + pseudo;

    return this.http.get(url, {withCredentials: true})
  }

  disconnectUser() {
    const url = this.fileUrl + "/disconnect"
    return this.http.get(url, {withCredentials: true, headers:null} );
  }

}
