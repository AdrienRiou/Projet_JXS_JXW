import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { LateralPanelComponent } from './lateral-panel/lateral-panel.component';
import { FilesDisplayComponent } from './files-display/files-display.component';
import { FileInfoComponent } from './file-info/file-info.component';


import {FlexLayoutModule} from '@angular/flex-layout';

@NgModule({
  declarations: [
    AppComponent,
    LateralPanelComponent,
    FilesDisplayComponent,
    FileInfoComponent
  ],
  imports: [
    BrowserModule,
    NgbModule.forRoot(),
    FlexLayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
