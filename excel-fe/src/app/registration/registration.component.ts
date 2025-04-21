import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  standalone: false,
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {
  registrationForm: FormGroup;
  response : any;

  constructor(private fb: FormBuilder, private authService: AuthService) {
    // Initialize the reactive form
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      const { username, email, password } = this.registrationForm.value;
      this.authService.register(username, email, password).subscribe(
        response => {
          // Handle successful registration
          this.response = response;
          //console.log('Registration successful', response);
          if(this.response.result === "Success") {
              console.log('Registration successful', response);
              alert('Registration successful!');
          }
          else if (this.response.result === "Exists") {
            alert('User already exists. Please choose a different email.');
          } 
        },
        error => {
          // Handle registration error
          //console.error('Registration failed', error);
          alert('Registration failed. Please try again.');
          
        }
      );
    } else {
      alert('Please fill in all required fields correctly.');
    }
  }
}