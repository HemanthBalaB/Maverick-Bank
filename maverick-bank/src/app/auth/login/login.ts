import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  loading = false;
  showPassword = false;

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}

  onSubmit() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Account No / Email and password required!';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const isEmployee = this.username.endsWith('@maverick.com'); // Adjust logic if needed

    if (isEmployee) {
      // 🧑‍💼 EMPLOYEE SESSION-BASED LOGIN
      this.http.post<any>('http://localhost:8081/api/employees/login', {
        username: this.username,
        password: this.password
      }, { withCredentials: true }).subscribe({
        next: (employee) => {
          this.loading = false;
          console.log('Logged in as employee:', employee);
          this.router.navigate(['/employee/dashboard']);
        },
        error: err => {
          this.loading = false;
          this.errorMessage = 'Invalid employee credentials. Please try again!';
        }
      });
    } else {
      // 🔐 USER/ADMIN JWT LOGIN
      this.authService.login(this.username, this.password).subscribe({
        next: () => {
          this.loading = false;
          const role = this.authService.getRole();

          console.log('Redirecting for role:', role);
          console.log('currentUser:', this.authService.getCurrentUser());

          if (role === 'ADMIN') {
            this.router.navigate(['/admin/dashboard']);
          } else if (role === 'USER' || role === 'CUSTOMER') {
            this.router.navigate(['/customer/dashboard']);
          } else {
            this.errorMessage = 'Unknown user role.';
          }
        },
        error: err => {
          this.loading = false;
          this.errorMessage = err.error?.message || 'Invalid credentials. Please try again!';
        }
      });
    }
  }
}
