import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalystPurchaseComponent } from './analyst-purchase.component';

describe('AnalystPurchaseComponent', () => {
  let component: AnalystPurchaseComponent;
  let fixture: ComponentFixture<AnalystPurchaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalystPurchaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalystPurchaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
