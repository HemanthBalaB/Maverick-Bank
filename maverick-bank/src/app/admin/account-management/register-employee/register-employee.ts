import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-employee.html',
  styleUrls: ['./register-employee.scss']
})
export class RegisterEmployeeComponent {
  employee = {
    name: '',
    email: '',
    mobileNo: '',
    password: '',
    address: ''
  };

  errorMessage = '';
  successMessage = '';
  showPassword = false;

  constructor(private http: HttpClient, private router: Router) {}

  register() {
    this.http.post('http://localhost:8081/api/employees/register', this.employee)
      .subscribe({
        next: () => {
          this.successMessage = 'Employee registered successfully!';
          setTimeout(() => this.router.navigate(['/admin/dashboard/manage-employee']), 1000);
        },
        error: () => this.errorMessage = 'Employee registered successfully!'
      });
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
