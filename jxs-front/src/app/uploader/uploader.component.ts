import { Component, OnInit } from '@angular/core';
import {UploaderService} from './uploader.service'

@Component({
  selector: 'app-uploader',
  templateUrl: './uploader.component.html',
  styleUrls: ['./uploader.component.css'],
  providers: [ UploaderService ]
})
export class UploaderComponent implements OnInit {

  private uploaderService: UploaderService

  constructor() { }

  onPicked(input: HTMLInputElement) {
    const file = input.files[0];
    if (file) {
      this.uploaderService.upload(file).subscribe(
      );
    }
  }
  ngOnInit() {
  }

}
