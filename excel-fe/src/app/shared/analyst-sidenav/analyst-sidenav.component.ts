import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

/**
 * @title Autosize sidenav
 */
@Component({
  selector: 'app-analyst-sidenav',
  templateUrl: './analyst-sidenav.component.html',
  styleUrls: ['./analyst-sidenav.component.scss'],
  standalone: false
})
export class AnalystSidenavComponent {
  @ViewChild('sidenav') sidenav?: MatSidenav;
  isExpanded = true;
  isMenuExpanded = false; // Track the menu state
  showSubmenu: boolean = false;
  isShowing = false;
  showSubSubMenu: boolean = false;
  role : string = '';

  constructor(private router: Router, private cookieService : CookieService) {}

  ngOnInit() {
    this.role = this.cookieService.get('role');
  }

  mouseenter() {
    if (!this.isExpanded) {
      this.isShowing = true;
    }
  }

  mouseleave() {
    if (!this.isExpanded) {
      this.isShowing = false;
    }
  }

  
  navigateToDashboard(): void {
    
    if (this.role.includes('Admin')) {
      this.router.navigate(['/admin-dashboard']); // Navigate to Admin Dashboard
    } else if (this.role.includes('Analyst')) {
      this.router.navigate(['/analyst-dashboard']); // Navigate to Analyst Dashboard
    } else {
      //console.error('Unknown role:', this.role);
      alert('You do not have access to a dashboard.');
    }
  }

  logout(): void {
    // Clear user session or token
    localStorage.clear(); // Example: Clear local storage
    sessionStorage.clear(); // Example: Clear session storage
    this.cookieService.deleteAll();
    // Redirect to login page
    this.router.navigate(['/login']);
  }

  toggleSubmenu(menu: 'showSubmenu' | 'showSubSubMenu'): void {
    this[menu] = !this[menu]; // Toggle the state of the specified menu
  }

  toggleMenu(){
    this.isMenuExpanded = !this.isMenuExpanded; // Toggle the menu state
  }

  onMenuItemClick(event: Event): void {
    event.preventDefault(); // Prevent default navigation behavior
    //console.log('Menu item clicked');
  }
}
