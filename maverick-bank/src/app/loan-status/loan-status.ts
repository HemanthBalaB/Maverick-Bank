import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService } from '../services/loan';
import { AuthService } from '../services/auth';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-loan-status',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './loan-status.html',
  styleUrls: ['./loan-status.scss']
})
export class LoanStatusComponent implements OnInit {
  loans: any[] = [];
  isLoading = true;
  errorMessage = '';
  currentUser: any;

  constructor(
    private loanService: LoanService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (!this.currentUser?.accountNo) {
      this.errorMessage = 'User not logged in or account not found';
      this.isLoading = false;
      return;
    }
    this.loadLoans();
  }

  loadLoans(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.loanService.getCustomerLoans(this.currentUser.accountNo).subscribe({
      next: (loans: any[]) => {
        this.loans = loans;
        this.isLoading = false;
      },
      error: (err: any) => {
        this.errorMessage = 'Failed to load loan applications';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  getStatusClass(status: string): string {
    if (!status) return '';
    switch (status.toLowerCase()) {
      case 'pending': return 'status-pending';
      case 'approved': return 'status-approved';
      case 'rejected': return 'status-rejected';
      case 'paid': return 'status-paid';
      default: return '';
    }
  }

  payLoan(loan: any): void {
    if (!loan?.id || !loan.amount) {
      alert('Invalid loan data');
      return;
    }

    const paymentAmount = prompt(`Enter payment amount (Loan Amount: ${loan.amount})`);
    if (!paymentAmount || isNaN(Number(paymentAmount))) {
      alert('Please enter a valid amount');
      return;
    }

    const amount = Number(paymentAmount);
    if (amount <= 0) {
      alert('Payment amount must be greater than 0');
      return;
    }

    const accountNo = this.currentUser.accountNo;
    this.loanService.makePayment(loan.id, accountNo, amount).subscribe({
      next: (response: { message: string }) => {
        alert(response.message);
        this.loadLoans();
      },
      error: (err: any) => {
        console.error('Error paying loan', err);
        alert(err.error?.message || 'Failed to pay loan');
      }
    });
  }
}