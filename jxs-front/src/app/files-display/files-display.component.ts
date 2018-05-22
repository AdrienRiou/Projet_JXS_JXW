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
  nomTab
  constructor( fs : FileService) {
    this.fs = fs;

  }
  refresh(){
    this.files.files = [];
    this.getFiles();
  }

  onSelect(fileParam : FileClass ):void{

    this.fs.changeService(fileParam.service)
    if(fileParam.isFolder){
      if(fileParam.service=="dropbox"){
        var str = "";
        console.log("hjfskhlksm")
        console.log(this.idTab)
        console.log("hjfskhlksm")
        for(var i = 1;i<this.idTab.length;i++){
          str = str + "/" + this.idTab[i];
        }
        str = "id:"+ fileParam.id;
        this.fs.getFileFolder(str).subscribe(data =>{
          this.files = <FileListClass>data;

      })

      }
      else{

          this.fs.getFileFolder(fileParam.id).subscribe(data =>{
            this.files = <FileListClass>data;

        })
      }
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
    this.files.files=[]
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
      if (this.fs.service.getValue()=="dropbox"){
      this.fs.getFileFolder("id:"+this.idTab[this.idTab.length-1]).subscribe(data =>{
        this.files = <FileListClass>data;

      })
    }
      else{
        this.fs.getFileFolder(this.idTab[this.idTab.length-1]).subscribe(data =>{
          this.files = <FileListClass>data;
        }
      )

    }
  }

  }

  ngOnInit() {
    this.getFiles();
  }

  getFiles() {

    this.fs.startDisplay = true
    this.fs.getAllFilesGoogle().subscribe(data  => {
      console.log("RETURN GET ALL = " + data)
      let filesGoogle = <FileListClass>data;

      console.log(filesGoogle)

      this.concatF(filesGoogle)
      console.log("filesGoogle")
      console.log(this.files)
      console.log("filesGoogle")


    });
    console.log("getFilesDropBox")
    this.fs.getAllFilesDropbox().subscribe(data  => {
      console.log("RETURN GET ALL = ")
      console.log(data)
      let filesDrop = <FileListClass>data;
      console.log("getFiles")
      console.log(filesDrop)
      console.log("getFiles")
      this.concatF(filesDrop)
    });

  }
  concatF(files : FileListClass){
    console.log("concatF")
    console.log(files.files)
    this.files.files = files.files.concat(this.files.files)
    console.log(this.files.files)
    console.log("concatF")
  }

}
