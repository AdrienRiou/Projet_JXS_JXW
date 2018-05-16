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
  ngOnInit() {
  /*  this.ss.fileSource.subscribe(file => {
      console.log(file)
      this.file = file
    });
*/

  }



}
