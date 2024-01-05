import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminManageInsurerComponent } from './admin-manage-insurer.component';

describe('AdminManageInsurerComponent', () => {
  let component: AdminManageInsurerComponent;
  let fixture: ComponentFixture<AdminManageInsurerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminManageInsurerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminManageInsurerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
