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

    console.log(fileParam);
    console.log (this.selectedFile);
    console.log("preDebut");
    console.log(this.ss.fileSource.getValue())
    console.log("postDebut");

    this.selectedFile = fileParam;

    console.log("preChange");
    console.log(this.ss.fileSource.getValue())
    console.log("preChange2");

    this.ss.changeFile(fileParam);

    console.log("postChange");
    console.log(this.ss.fileSource.getValue());
    console.log("postChange2");

    this.ss.fileSource.subscribe(selectedFile => {
      console.log("preSubscribe")
      console.log(selectedFile)
      console.log("postSubscribe")
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
