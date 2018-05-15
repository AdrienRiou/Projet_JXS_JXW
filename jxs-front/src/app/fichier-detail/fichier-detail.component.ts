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
  file: FileClass = {name:"name", id:"id", lastEditDate:"lastEditDate", size:"size",
  creationDate:"creationDate", authors:[]};
  data : any ={};

  constructor(ss: SharedService, local: Location, fs :FileService) {
      this.ss = ss
      this.local= local
      this.fs = fs
  }

  goBack(): void {
    this.fs.tryParsing().subscribe(data  => { this.data = data;
      /*this.data.forEach(elem => {console.log("jsp" + el.html_url)*/
      this.data.forEach(elem => {
        let size = elem.size;
        let lastEditDate = elem.lastEditDate;
        let id = elem.id;
        let name = elem.name;
        let creationDate = elem.creationDate;
        let authors = elem.authors;
        this.fs.addListFiles({name:name, id:id, lastEditDate:lastEditDate, size:size,
        creationDate:creationDate, authors:authors })
      });
    });

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
