import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExpertiseComponent } from './add-expertise.component';

describe('AddExpertiseComponent', () => {
  let component: AddExpertiseComponent;
  let fixture: ComponentFixture<AddExpertiseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddExpertiseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddExpertiseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
