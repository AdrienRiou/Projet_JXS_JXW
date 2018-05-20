import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {ConnectJsonInterface, ConnectJsonClass} from '../ConnectJsonInterface'
import {FileService} from '../file.service'

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
  urlCo : string = "https://jxs-back.herokuapp.com/rest/api/";
  fs : FileService;
  userName : string;
  constructor(http : HttpClient, connectedJson : ConnectJsonClass, fs : FileService
  ) {
    this.fs = fs;
    this.http=http
    this.connectedJson = connectedJson
  }

  ngOnInit() {
    console.log("providers: [NameService]")
    const url = this.urlCo + "isconnected"
    this.http.get<ConnectJsonInterface>(url).subscribe(data =>{
      this.connectedJson = <ConnectJsonInterface>data
    });
    this.google_auth = this.connectedJson.isConnected;
    this.pseudo = this.connectedJson.pseudo;
  }

  disconnect(){
    const url = this.urlCo + "disconnect"
    this.http.get(url).subscribe()
  }

  addUser() {
    this.fs.connectUser(this.userName).subscribe(data  => {
      console.log(data);
    });;
  }

}
