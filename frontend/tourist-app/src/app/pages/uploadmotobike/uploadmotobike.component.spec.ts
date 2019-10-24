import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadmotobikeComponent } from './uploadmotobike.component';

describe('UploadmotobikeComponent', () => {
  let component: UploadmotobikeComponent;
  let fixture: ComponentFixture<UploadmotobikeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadmotobikeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadmotobikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
