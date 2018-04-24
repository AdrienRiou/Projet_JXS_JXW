import { Injectable } from '@angular/core';

import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
@Injectable()
export class ApiService {

  constructor(private http : HttpClient) { }

  public connectAPI(url : String) {
    this.http.get(url).subscribe( data => {
      console.log(data);
    })
  }

}
