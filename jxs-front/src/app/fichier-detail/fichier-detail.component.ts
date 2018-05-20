import { Component, OnInit, Input } from '@angular/core';
import { FileClass, FileListClass } from '../FileClass';
import {SharedService} from '../shared.service'
import { Location } from '@angular/common'
import { ActivatedRoute } from '@angular/router';
import {FileService} from '../file.service';


@Component({
  selector: 'app-fichier-detail',
  templateUrl: './fichier-detail.component.html',
  styleUrls: ['./fichier-detail.component.css']
})

export class FichierDetailComponent implements OnInit {
  ss : SharedService
  local : Location
  subscription : any
  fs : FileService
  route: ActivatedRoute
  files  : FileListClass;
  file : FileClass /*= {name:"name", id:"id", lastEditDate:"lastEditDate", size:"size",
  creationDate:"creationDate", authors:[]  }*/;
  renameVar : string;

  constructor(ss: SharedService, local: Location, fs :FileService) {
      this.ss = ss
      this.local= local
      this.fs = fs

  }

  goBack(): void {
    this.fs.getAllFiles().subscribe(data  => {
      this.files = <FileListClass>data;
    });
  }

  delete():void{
    this.fs.removeFile(this.ss.fileSource.getValue().id).subscribe();
    console.log(this.ss.fileSource.getValue().id);
  }
  rename():void{
    console.log("rename " + this.renameVar)

  }
  ngOnInit() {
    this.ss.fileSource.subscribe(file =>
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
