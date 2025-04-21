import { Component } from '@angular/core';

@Component({
  selector: 'app-root', // Ensure this matches <app-root> in index.html
  templateUrl: './app.component.html',
  standalone: false,
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Angular Excel App';
}