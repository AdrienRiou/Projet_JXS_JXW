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
  files : FileListClass
  selectedFile : FileClass;
  idTab : string[];
  constructor( fs : FileService) {
    this.fs = fs;
  }
  onSelect(fileParam : FileClass ):void{

    if(fileParam.isFolder){
      this.fs.getFileFolder(fileParam.id).subscribe(data =>{
        this.files = <FileListClass>data;
      })
      this.idTab.push(this.selectedFile.id)
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
    if(this.idTab.length == 0){
      this.fs.getAllFiles().subscribe(data  => {
        this.files = <FileListClass>data;
      })
    }
    else{
      this.fs.getFileFolder(this.idTab[this.idTab.length-1]).subscribe(data =>{
        this.files = <FileListClass>data;
      })
      this.idTab.pop();
    }

  }

  ngOnInit() {
    this.getFiles();
  }

  getFiles() {
    console.log("getFiles")
    this.fs.startDisplay = true
    this.fs.getAllFiles().subscribe(data  => {
      console.log("RETURN GET ALL = " + data)
      this.files = <FileListClass>data;

    });
  }

}
