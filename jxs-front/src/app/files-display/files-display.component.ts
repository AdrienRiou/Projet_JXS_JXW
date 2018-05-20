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
  ngOnInit() {
    this.getFiles();
  }

  getFiles() {
    this.fs.getAllFiles().subscribe(data  => {
      console.log("RETURN GET ALL = " + data)
      this.files = <FileListClass>data;
    });
  }

}
