import { Component, OnInit } from '@angular/core';
import { LoanService } from '../services/loan';
import { AuthService } from '../services/auth';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './loan-list.html', // Fixed file extension
  styleUrls: ['./loan-list.scss']  // Fixed file extension
})
export class LoanListComponent implements OnInit {
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
    this.loadLoans();
  }

  loadLoans(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.loanService.getCustomerLoans(this.currentUser.accountNo).subscribe({
      next: (loans) => {
        this.loans = loans;
        this.isLoading = false;
      },
      error: (err) => {
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

  makePayment(loan: any): void {
    const amount = prompt(`Enter payment amount for loan ${loan.id}`);
    if (!amount || isNaN(Number(amount))) return;

    this.loanService.makePayment(loan.id, loan.accountNo, Number(amount)).subscribe({
      next: (message) => {
        alert(message);
        this.loadLoans();
      },
      error: (err) => {
        alert('Payment failed');
      }
    });
  }
}