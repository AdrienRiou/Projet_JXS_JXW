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
  home(){
    this.files.files = [];
    this.idTab = [""];
    this.getFiles();
  }

  navigateTo(folder : FileClass) :void {
    this.fs.changeService(folder.service)
    if(folder.service=="dropbox"){
      var str = "";
      for(var i = 1;i<this.idTab.length;i++){
        str = str + "/" + this.idTab[i];
      }
      str = "id:"+ folder.id;
      this.fs.getFileFolder(str).subscribe(data =>{
        this.files = <FileListClass>data;

    })
    }
    else{

        this.fs.getFileFolder(folder.id).subscribe(data =>{
          this.files = <FileListClass>data;

      })
    }
    this.idTab.push(folder.id)
  }

  onSelect(fileParam : FileClass ):void{

    this.fs.changeService(fileParam.service)

    this.selectedFile = fileParam;

    this.fs.changeFile(fileParam);

    this.fs.fileSource.subscribe(selectedFile => {

      this.selectedFile = selectedFile
    })

  }

  back(){
    this.files.files=[]
    if(this.idTab.length > 1){
      this.idTab.pop();
    }
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
      let filesGoogle = <FileListClass>data;
      this.concatF(filesGoogle)


    });
    this.fs.getAllFilesDropbox().subscribe(data  => {
      let filesDrop = <FileListClass>data;
      this.concatF(filesDrop)
    });

  }
  concatF(files : FileListClass){
    if ( files.files ) {
      this.files.files = files.files.concat(this.files.files)
      this.files.files = this.files.files.sort (n1 => {
        if n1.isFolder {
          return 1;
        } else {
          return 0;
        }
      })
    }
  }

}
