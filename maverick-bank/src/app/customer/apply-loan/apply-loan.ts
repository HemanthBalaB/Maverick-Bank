// ============================
// ✅ apply-loan.component.ts
// ============================
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-apply-loan',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './apply-loan.html',
  styleUrls: ['./apply-loan.scss']
})
export class ApplyLoan implements OnInit {
  accountNo: number = 0;
  loanAmount: number = 0;
  tenureInMonths: number = 0;
  purpose: string = '';
  message: string = '';
  error: string = '';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    this.accountNo = +(user?.accountNo ?? user?.username ?? 0);
  }

  apply(): void {
    if (!this.loanAmount || !this.tenureInMonths || !this.purpose) {
      this.error = 'Please fill all the fields.';
      return;
    }

    const loan = {
      accountNo: this.accountNo,
      loanAmount: this.loanAmount,
      tenureInMonths: this.tenureInMonths,
      purpose: this.purpose
    };

    this.http.post('http://localhost:8081/api/loans/apply', loan, { responseType: 'text' })
      .subscribe({
        next: (res) => {
          this.message = res;
          this.error = '';
          this.loanAmount = 0;
          this.tenureInMonths = 0;
          this.purpose = '';
        },
        error: () => {
          this.error = 'Loan application failed.';
          this.message = '';
        }
      });
  }
}
