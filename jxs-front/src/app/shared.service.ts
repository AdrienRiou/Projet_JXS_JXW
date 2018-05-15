import { Component, Injectable,Input,Output,EventEmitter } from '@angular/core';
import {FileClass} from './FileClass';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable(/*
  // we declare that this service should be created
  // by the root application injector.

  providedIn: 'root',
}*/)
export class SharedService {
    public fileSource = new BehaviorSubject<FileClass>({name:"name", id:"id", lastEditDate:"lastEditDate", size:"size",
    creationDate:"creationDate", authors:[]  });

    currentFile = this.fileSource.asObservable();

     constructor() {
       console.log('shared service started');
     }

     changeFile(file:FileClass){
       this.fileSource.next(file);
     }




}
