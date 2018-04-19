import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LateralPanelComponent } from './lateral-panel.component';

describe('LateralPanelComponent', () => {
  let component: LateralPanelComponent;
  let fixture: ComponentFixture<LateralPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LateralPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LateralPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
