import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { LateralPanelComponent } from './lateral-panel/lateral-panel.component';
import { FilesDisplayComponent } from './files-display/files-display.component';
import { FileInfoComponent } from './file-info/file-info.component';
import { HttpClientModule } from '@angular/common/http';
import {FlexLayoutModule} from '@angular/flex-layout';

import { ApiService } from './api.service'
@NgModule({
  declarations: [
    AppComponent,
    LateralPanelComponent,
    FilesDisplayComponent,
    FileInfoComponent
  ],
  imports: [
    BrowserModule,
    FlexLayoutModule,
    HttpClientModule,
  ],
  providers: [
    ApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
