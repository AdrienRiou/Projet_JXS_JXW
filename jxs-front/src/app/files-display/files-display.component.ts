import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {FileListClass, FileClass} from '../FileClass'

import {FileService} from '../file.service'

@Component({
  selector: 'app-files-display',
  templateUrl: './files-display.component.html',
  styleUrls: ['./files-display.component.css']
})
export class FilesDisplayComponent implements OnInit {
  fs : FileService
  files : FileListClass = <FileListClass>{ files : []};
  selectedFile : FileClass;
  previousFolderId : string = "";
  idTab : Array<string> = [""];
  constructor( fs : FileService) {
    this.fs = fs;

  }
  onSelect(fileParam : FileClass ):void{
    this.fs.changeService(fileParam.service)
    if(fileParam.isFolder){
      this.fs.getFileFolder(fileParam.id).subscribe(data =>{
        this.files = <FileListClass>data;
      })
      this.idTab.push(fileParam.id)
      this.previousFolderId = fileParam.id
      console.log("ONSELECT")
      console.log(this.idTab)
      console.log("ONSELECT")

    }

    this.selectedFile = fileParam;

    this.fs.changeFile(fileParam);
    console.log("onSelect")
    console.log(this.selectedFile)

    this.fs.fileSource.subscribe(selectedFile => {

      this.selectedFile = selectedFile
    })



    console.log("END");
  }

  back(){

    if(this.idTab.length > 1){
      this.idTab.pop();
    }
    console.log("BACK")
    console.log(this.idTab)
    console.log("BACK")
    if(this.idTab[this.idTab.length-1] == ""){
      this.getFiles();
    }
    else{
      this.fs.getFileFolder(this.idTab[this.idTab.length-1]).subscribe(data =>{
        this.files = <FileListClass>data;

      })

    }

  }

  ngOnInit() {
    this.getFiles();
  }

  getFiles() {

    this.fs.startDisplay = true
    this.fs.getAllFilesGoogle().subscribe(data  => {
      console.log("RETURN GET ALL = " + data)
      this.files = <FileListClass>data;

    });
    console.log("getFilesDropBox")
    this.fs.getAllFilesDropbox().subscribe(data  => {
      console.log("RETURN GET ALL = ")
      console.log(data)
      let filesDrop = <FileListClass>data;
      console.log("getFiles")
      console.log(this.files)
      console.log("getFiles")
      this.files.concatFiles(filesDrop)
    });

  }

}
