import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminManageExpertComponent } from './admin-manage-expert.component';

describe('AdminManageExpertComponent', () => {
  let component: AdminManageExpertComponent;
  let fixture: ComponentFixture<AdminManageExpertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminManageExpertComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminManageExpertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
