import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadcarComponent } from './uploadcar.component';

describe('UploadcarComponent', () => {
  let component: UploadcarComponent;
  let fixture: ComponentFixture<UploadcarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadcarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadcarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
