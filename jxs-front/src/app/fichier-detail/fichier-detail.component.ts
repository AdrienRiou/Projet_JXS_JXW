import { Component, OnInit, Input } from '@angular/core';
import { FileClass } from '../FileClass';
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
  file: FileClass = { id: 11, name: 'file11' };
  data : any ={};

  constructor(ss: SharedService, local: Location, fs :FileService) {
      this.ss = ss
      this.local= local
      this.fs = fs
  }

  goBack(): void {
    this.fs.tryParsing().subscribe(data  => {this.data = data;
      let x = this.data[0];
      console.log("goBack " + x)});

              // here result would have json object that was parsed by map handler...


    this.local.back();
  }
  ngOnInit() {
    this.ss.fileSource.subscribe(file => {
      console.log(file)
      this.file = file
    });


  }



}
