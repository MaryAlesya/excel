import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../shared/services/auth.service';
import { crypt } from '../shared/utility/crypt';
import { Router } from '@angular/router'; // Import Router
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: false,
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, 
    private authService: AuthService, 
    private router: Router,
    private cookieService: CookieService,) {
    // Initialize the reactive form
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      const encryptedpassword= crypt.encrypt(password.toString());
      this.authService.login(username, encryptedpassword.toString()).subscribe(
        (response) => {
          const data = response.role.split(','); // Split roles into an array
          this.cookieService.set('user_id', data[1].split('_')[1], 1, '/'); // Cookie expires in 1 day
          this.cookieService.set('token', response.token, 1, '/');
          this.cookieService.set('role', data[0], 1, '/');
           // Navigate to the appropriate dashboard based on the role
            if (response.role.includes('Admin')) {
              this.router.navigate(['/admin-dashboard']);
            } else if (response.role.includes('Analyst')) {
              this.router.navigate(['/analyst-dashboard']);
            }
            else {
              alert('Unauthorized role');
            }
            },
        (error) => {
          // Handle login error
          //console.error('Login error', error);
          alert('Invalid username or password');
        }
      );
    } else {
      alert('Please fill in both username and password.');
    }
  }
}