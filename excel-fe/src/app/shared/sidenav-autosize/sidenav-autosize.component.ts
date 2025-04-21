import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

/**
 * @title Autosize sidenav
 */
@Component({
  selector: 'app-sidenav-autosize',
  templateUrl: 'sidenav-autosize.component.html',
  styleUrls: ['sidenav-autosize.component.scss'],
  standalone: false
})
export class SidenavAutosize {
  @ViewChild('sidenav') sidenav?: MatSidenav;
  isExpanded = true;
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

  toggleSubmenu(menu: 'showSubmenu' | 'showSubSubMenu'): void {
    this[menu] = !this[menu]; // Toggle the state of the specified menu
  }

  logout(): void {
    // Clear user session or token
    localStorage.clear(); // Example: Clear local storage
    sessionStorage.clear(); // Example: Clear session storage
    this.cookieService.deleteAll();
    // Redirect to login page
    this.router.navigate(['/login']);
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

}


/**  Copyright 2017 Google Inc. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at http://angular.io/license */