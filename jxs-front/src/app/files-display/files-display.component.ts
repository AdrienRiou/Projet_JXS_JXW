import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {FileListClass, FileClass} from '../FileClass'
import {SharedService} from '../shared.service'
import {FileService} from '../file.service'

@Component({
  selector: 'app-files-display',
  templateUrl: './files-display.component.html',
  styleUrls: ['./files-display.component.css']
})
export class FilesDisplayComponent implements OnInit {
  fs : FileService
  files : FileListClass;
  selectedFile : FileClass;
  constructor( fs : FileService) {
    this.fs = fs;
  }
<<<<<<< HEAD
  onSelect(fileParam : FileClass ):void{



    this.selectedFile = fileParam;

    this.fs.changeFile(fileParam);

    this.fs.fileSource.subscribe(selectedFile => {

      this.selectedFile = selectedFile
    })
    console.log("END");
=======
  ngOnInit() {
    this.getFiles();
>>>>>>> 6ab4800f9ecbaff4e09ec27645b6aafe98b1df30
  }

  getFiles() {
    this.fs.getAllFiles().subscribe(data  => {
      console.log("RETURN GET ALL = " + data)
      this.files = <FileListClass>data;
    });
  }

}
