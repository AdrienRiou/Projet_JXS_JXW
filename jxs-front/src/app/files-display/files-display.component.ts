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
  ss : SharedService
  constructor(ss: SharedService, fs : FileService) {
    this.fs = fs;
    this.ss = ss;
  }
  onSelect(fileParam : FileClass ):void{



    this.selectedFile = fileParam;

    this.fs.changeFile(fileParam);

    this.fs.fileSource.subscribe(selectedFile => {

      this.selectedFile = selectedFile
    })
    console.log("END");
  }

  ngOnInit() {
    this.fs.getAllFiles().subscribe(data  => {
      this.files = <FileListClass>data;
    });
  }

}
