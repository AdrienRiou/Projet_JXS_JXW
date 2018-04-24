import { Component, OnInit } from '@angular/core';
import { ApiService} from "../api.service";
@Component({
  selector: 'app-lateral-panel',
  templateUrl: './lateral-panel.component.html',
  styleUrls: ['./lateral-panel.component.css']
})
export class LateralPanelComponent implements OnInit {

  google_auth : boolean = false;
//  const google_url : String = "https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.metadata.readonly&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&response_type=code&client_id=752520275648-e65rp9l865vctpj0535kpoh0bfo5pkd0.apps.googleusercontent.com&redirect_uri=http://localhost:8080/rest/redirect/google ";
  const google_url : String = "http://localhost:8080/rest/myresource/easy";
  constructor(
    private api : ApiService
  ) {}

  ngOnInit() {
  }


  getGoogleAccess() {
    this.google_auth=this.api.connectAPI(this.google_url);
  }
}
