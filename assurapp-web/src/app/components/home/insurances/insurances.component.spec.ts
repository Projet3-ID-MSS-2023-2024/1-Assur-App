import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsurancesComponent } from './insurances.component';

describe('InsurancesComponent', () => {
  let component: InsurancesComponent;
  let fixture: ComponentFixture<InsurancesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InsurancesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InsurancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
