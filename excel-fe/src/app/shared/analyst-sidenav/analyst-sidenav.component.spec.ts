import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalystSidenavComponent } from './analyst-sidenav.component';

describe('AnalystSidenavComponent', () => {
  let component: AnalystSidenavComponent;
  let fixture: ComponentFixture<AnalystSidenavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalystSidenavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalystSidenavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
