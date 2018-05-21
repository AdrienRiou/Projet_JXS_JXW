import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import {FileService} from './file.service';
import { HttpClientModule } from '@angular/common/http';


import { AppComponent } from './app.component';
import { FilesDisplayComponent } from './files-display/files-display.component';
import { LateralPanelComponent } from './lateral-panel/lateral-panel.component';
import { FileInfoComponent } from './file-info/file-info.component';
import { FichierDetailComponent } from './fichier-detail/fichier-detail.component';
import { AppRoutingModule } from './app-routing.module';
import {UploaderComponent} from './uploader/uploader.component'
import {CookieService} from 'angular2-cookie/core';






@NgModule({
  declarations: [
    AppComponent,
    FilesDisplayComponent,
    LateralPanelComponent,
    FileInfoComponent,
    FichierDetailComponent,
    UploaderComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule

  ],
  providers: [FileService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
