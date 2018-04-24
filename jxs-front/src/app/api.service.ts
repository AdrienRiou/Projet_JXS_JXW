import { Injectable } from '@angular/core';


import {HttpClient} from "@angular/common/http";
@Injectable()
export class ApiService {

  constructor(private http : HttpClient) { }

  public connectAPI(url) {
    var res  = false
    this.http.get(url).subscribe( data => {
      console.log(data);
    });
    return res;
  }

}
