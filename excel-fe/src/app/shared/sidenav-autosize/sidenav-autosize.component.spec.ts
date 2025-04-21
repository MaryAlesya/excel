import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavAutosizeComponent } from './sidenav-autosize.component';

describe('SidenavAutosizeComponent', () => {
  let component: SidenavAutosizeComponent;
  let fixture: ComponentFixture<SidenavAutosizeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidenavAutosizeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidenavAutosizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
