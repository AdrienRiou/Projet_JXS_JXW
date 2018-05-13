import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FichierDetailComponent } from './fichier-detail.component';

describe('FichierDetailComponent', () => {
  let component: FichierDetailComponent;
  let fixture: ComponentFixture<FichierDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FichierDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FichierDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
