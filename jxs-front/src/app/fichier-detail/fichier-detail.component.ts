import { Component, OnInit, Input } from '@angular/core';
import { FileClass, FileListClass } from '../FileClass';

import { Location } from '@angular/common'
import { ActivatedRoute } from '@angular/router';
import {FileService} from '../file.service';


@Component({
  selector: 'app-fichier-detail',
  templateUrl: './fichier-detail.component.html',
  styleUrls: ['./fichier-detail.component.css']
})

export class FichierDetailComponent implements OnInit {
  local : Location
  subscription : any
  fs : FileService
  route: ActivatedRoute
  files  : FileListClass;
  file : FileClass /*= {name:"name", id:"id", lastEditDate:"lastEditDate", size:"size",
  creationDate:"creationDate", authors:[]  }*/;
  renameVar : string;

  constructor(local: Location, fs :FileService) {
      this.local= local
      this.fs = fs

  }


  delete():void{
    if (this.fs.service.getValue()=="dropbox"){
      this.fs.removeFile("id:"+this.fs.fileSource.getValue().id).subscribe();
      console.log(this.fs.fileSource.getValue().id);
    }
    else{
    this.fs.removeFile(this.fs.fileSource.getValue().id).subscribe();
    console.log(this.fs.fileSource.getValue().id);
  }
  }
  rename():void{
    console.log("rename " + this.renameVar);
    this.fs.renameFile(this.fs.fileSource.getValue().id + "/" + this.renameVar).subscribe();

  }
  ngOnInit() {
    this.fs.fileSource.subscribe(file =>
      this.file = file)
      console.log("ngOnInit " + this.file)
      console.log("rename " + this.renameVar)
  /*  this.ss.fileSource.subscribe(file => {
      console.log(file)
      this.file = file
    });
*/

  }



}
