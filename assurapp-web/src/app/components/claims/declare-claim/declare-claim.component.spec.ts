import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeclareClaimComponent } from './declare-claim.component';

describe('DeclareClaimComponent', () => {
  let component: DeclareClaimComponent;
  let fixture: ComponentFixture<DeclareClaimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeclareClaimComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeclareClaimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
