import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPasswordFormStepComponent } from './forgot-password-form-step.component';

describe('ForgotPasswordFormStepComponent', () => {
  let component: ForgotPasswordFormStepComponent;
  let fixture: ComponentFixture<ForgotPasswordFormStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForgotPasswordFormStepComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ForgotPasswordFormStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
