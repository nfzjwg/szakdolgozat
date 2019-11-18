import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMotobikeComponent } from './edit-motobike.component';

describe('EditMotobikeComponent', () => {
  let component: EditMotobikeComponent;
  let fixture: ComponentFixture<EditMotobikeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditMotobikeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditMotobikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
