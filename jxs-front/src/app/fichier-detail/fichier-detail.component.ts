import { Component, OnInit, Input } from '@angular/core';
import { FileClass } from '../FileClass';
import {SharedService} from '../shared.service'


@Component({
  selector: 'app-fichier-detail',
  templateUrl: './fichier-detail.component.html',
  styleUrls: ['./fichier-detail.component.css']
})

export class FichierDetailComponent implements OnInit {
  ss : SharedService
  subscription : any
  file: FileClass = { id: 11, name: 'file11' };
  constructor(ss: SharedService) {
      this.ss = ss
  }

  ngOnInit() {
    this.ss.fileSource.subscribe(file => {
      console.log(file)
      this.file = file
    });


  }

}
