import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartXlComponent } from './cart-xl.component';

describe('CartXlComponent', () => {
  let component: CartXlComponent;
  let fixture: ComponentFixture<CartXlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartXlComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CartXlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
