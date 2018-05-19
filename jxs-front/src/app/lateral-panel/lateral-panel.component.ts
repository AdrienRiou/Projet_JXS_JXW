import { Component, OnInit } from '@angular/core';
import {FileService} from '../file.service'

@Component({
  selector: 'app-lateral-panel',
  templateUrl: './lateral-panel.component.html',
  styleUrls: ['./lateral-panel.component.css']
})
export class LateralPanelComponent implements OnInit {
  fs : FileService
  userName : string

  google_auth : boolean = false;
  constructor(fs : FileService) {
    this.fs = fs;
  }

  ngOnInit() {
  }

  addUser() {
    this.fs.connectUser(this.userName).subscribe(data  => {
      console.log(data);
    });;
  }

}
