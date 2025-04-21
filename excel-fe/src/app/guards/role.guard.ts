import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRole = route.data['role']; // Role required for the route
    const userRole = this.cookieService.get('role'); // Get the user's role from the cookie

    if (userRole && userRole === expectedRole) {
      return true; // Allow access if the role matches
    }

    // Redirect to login or unauthorized page if the role doesn't match
    this.router.navigate(['/unauthorized']);
    return false;
  }
}