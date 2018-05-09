import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import {FileClass} from './FileClass'
import {FichierDetailComponent} from './fichier-detail/fichier-detail.component'
import {FilesDisplayComponent} from './files-display/files-display.component'



const routes: Routes =[
  {path:'home', component:FilesDisplayComponent },
  {path:'fichier-detail', component: FichierDetailComponent}
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes)

  ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule{
}
