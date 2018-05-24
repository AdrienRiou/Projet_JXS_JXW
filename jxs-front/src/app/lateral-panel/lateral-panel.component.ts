import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {ConnectJsonInterface, ConnectJsonClass} from '../ConnectJsonInterface'
import {FileService} from '../file.service'
import { CookieService } from 'angular2-cookie/core';

@Component({
  selector: 'app-lateral-panel',
  templateUrl: './lateral-panel.component.html',
  styleUrls: ['./lateral-panel.component.css'],
  providers: [ConnectJsonClass]
})
export class LateralPanelComponent implements OnInit {
  http : HttpClient ;
  connectedJson : ConnectJsonClass = {isConnected : false, pseudo :"" };
  google_auth : boolean = false;
  pseudo : string;
  fs : FileService;
  cs : CookieService;
  userName : string;
  constructor(http : HttpClient, connectedJson : ConnectJsonClass, fs : FileService, cs : CookieService
  ) {

    this.cs = cs;
    this.fs = fs;
    this.http=http;
    this.connectedJson = connectedJson;
  }

  ngOnInit() {
    console.log("Connected as : " + this.cs.get("pseudo"))
    this.pseudo = this.cs.get("pseudo");
  }
  refresh(){
    this.ngOnInit()
  }
  disconnect(){
    this.fs.disconnectUser().subscribe();
    this.pseudo = null;
  }

  addUser() {

    this.fs.connectUser(this.userName).subscribe(data  => {
      console.log("addUser")
      console.log(data);
      this.pseudo = this.cs.get("pseudo");
    });;

  }

}
