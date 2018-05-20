import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {ConnectJsonInterface, ConnectJsonClass} from '../ConnectJsonInterface'


@Component({
  selector: 'app-lateral-panel',
  templateUrl: './lateral-panel.component.html',
  styleUrls: ['./lateral-panel.component.css'],
  providers: [ConnectJsonClass]
})
export class LateralPanelComponent implements OnInit {
  http : HttpClient ;
  connectedJson : ConnectJsonClass = {isConnected : true, pseudo :"izflj" };
  google_auth : boolean = true;
  pseudo : string = "";
  urlCo : string = "http://localhost:8080/rest/api/";
  constructor(http : HttpClient, connectedJson : ConnectJsonClass
  ) {
    this.http=http
    this.connectedJson = connectedJson
  }

  ngOnInit() {
    console.log("providers: [NameService]")
    const url = this.urlCo + "isconnected"
    this.http.get<ConnectJsonInterface>(url, {withCredentials: true, headers:null}).subscribe(data =>{
      console.log("regarderrr : " + JSON.stringify(this.connectedJson))
      this.connectedJson = <ConnectJsonInterface>data
    });
    console.log("regarder : " + JSON.stringify(this.connectedJson))
    this.google_auth = this.connectedJson.isConnected;
    this.pseudo = this.connectedJson.pseudo;
  }
  refresh(){
    this.ngOnInit()
  }
  disconnect(){
    const url = this.urlCo + "disconnect"
    this.http.get(url, {withCredentials: true, headers:null}).subscribe()
  }

  addUser() {

  }

}
