import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowClaimComponent } from './show-claim.component';

describe('ShowClaimComponent', () => {
  let component: ShowClaimComponent;
  let fixture: ComponentFixture<ShowClaimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowClaimComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowClaimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
