import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageSenderFormComponent } from './message-sender-form.component';

describe('MessageSenderFormComponent', () => {
  let component: MessageSenderFormComponent;
  let fixture: ComponentFixture<MessageSenderFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageSenderFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageSenderFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
