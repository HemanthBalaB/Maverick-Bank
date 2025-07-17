import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router'; // ✅ include RouterModule
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.html',
  styleUrls: ['./register.scss'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  mobileNo = '';
  role = '';
  errorMessage = '';
  successMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  onRegister() {
  if (this.role !== 'CUSTOMER') {
    this.errorMessage = 'Only CUSTOMER registration is supported here.';
    this.successMessage = '';
    return;
  }

  const payload = {
    name: this.username,
    email: this.email,
    password: this.password,
    mobileNo: this.mobileNo
  };

  this.http.post<any>('http://localhost:8081/createAccount', payload).subscribe({
    next: (response) => {
      if (response?.accountNo) {
        this.successMessage = `✅ ${response.message} Your Account Number is ${response.accountNo}. Please save it to log in.`;
        this.errorMessage = '';

        // Optional: redirect to login after 6 seconds
        setTimeout(() => this.router.navigate(['/login']), 6000);
      } else {
        this.successMessage = '';
        this.errorMessage = 'Something went wrong. Please try again.';
      }
    },
    error: (error) => {
      if (error.status === 500 && error.error?.message?.includes('mobile_no')) {
        this.errorMessage = 'Mobile number already exists. Please use a different one.';
      } else {
        this.errorMessage = error?.error?.message || 'Registration failed.';
      }
      this.successMessage = '';
    }
  });
}
}