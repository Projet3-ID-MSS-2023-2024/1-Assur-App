import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsurancesDashboardComponent } from './insurances-dashboard.component';

describe('InsurancesDashboardComponent', () => {
  let component: InsurancesDashboardComponent;
  let fixture: ComponentFixture<InsurancesDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InsurancesDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InsurancesDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
