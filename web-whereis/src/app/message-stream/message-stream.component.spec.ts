import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageStreamComponent } from './message-stream.component';

describe('MessageStreamComponent', () => {
  let component: MessageStreamComponent;
  let fixture: ComponentFixture<MessageStreamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessageStreamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageStreamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
