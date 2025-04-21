import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalystSalesComponent } from './analyst-sales.component';

describe('AnalystSalesComponent', () => {
  let component: AnalystSalesComponent;
  let fixture: ComponentFixture<AnalystSalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalystSalesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalystSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
