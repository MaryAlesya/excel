import { Component } from '@angular/core';

@Component({
  selector: 'app-left-nav',
  templateUrl: './left-nav.component.html',
  styleUrls: ['./left-nav.component.scss'],
  standalone: false
})
export class LeftNavComponent {
  isPanelCollapsed = false;

  togglePanel(): void {
    this.isPanelCollapsed = !this.isPanelCollapsed;
  }
}