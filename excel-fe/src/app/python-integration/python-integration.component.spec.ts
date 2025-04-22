import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PythonIntegrationComponent } from './python-integration.component';

describe('PythonIntegrationComponent', () => {
  let component: PythonIntegrationComponent;
  let fixture: ComponentFixture<PythonIntegrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PythonIntegrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PythonIntegrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
