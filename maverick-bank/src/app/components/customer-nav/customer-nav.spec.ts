import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerNav } from './customer-nav';

describe('CustomerNav', () => {
  let component: CustomerNav;
  let fixture: ComponentFixture<CustomerNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerNav]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerNav);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
