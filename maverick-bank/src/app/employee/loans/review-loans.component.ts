// src/app/employee/review-loans/review-loans.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface LoanApplication {
  loanId: number;
  accountNo: number;
  loanAmount: number;
  purpose: string;
  status: string;
  appliedDate: string;
}

@Component({
  selector: 'app-review-loans',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './review-loans.component.html',
  styleUrls: ['./review-loans.component.scss']
})
export class ReviewLoansComponent implements OnInit {
  loanApplications: LoanApplication[] = [];
  searchLoan: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadLoanApplications();
  }

  loadLoanApplications() {
    this.http.get<LoanApplication[]>('http://localhost:8081/api/loans/all').subscribe({
      next: data => this.loanApplications = data,
      error: err => this.errorMessage = 'Failed to load loan applications'
    });
  }

  filteredLoans(): LoanApplication[] {
    const term = this.searchLoan.toLowerCase();
    return this.loanApplications.filter(loan =>
      loan.accountNo.toString().includes(term) || loan.status.toLowerCase().includes(term)
    );
  }

approveLoan(loanId: number, accountNo: number) {
  this.http.post(
    `http://localhost:8081/api/loans/approve/${loanId}/${accountNo}`,
    {},
    {
      withCredentials: true,
      responseType: 'text' // ✅ Tell Angular this is a plain text response
    }
  ).subscribe({
    next: (res) => {
      console.log('Loan approved:', res);
      this.loadLoanApplications();
    },
    error: (err) => {
      console.error('Loan approval error:', err);
      this.errorMessage = 'Failed to approve loan';
    }
  });
}

rejectLoan(loanId: number, accountNo: number) {
  this.http.post(
    `http://localhost:8081/api/loans/reject/${loanId}/${accountNo}`,
    {},
    {
      withCredentials: true,
      responseType: 'text' // ✅ Handle plain text properly
    }
  ).subscribe({
    next: (res) => {
      console.log('Loan rejected:', res);
      this.loadLoanApplications();
    },
    error: (err) => {
      console.error('Loan rejection error:', err);
      this.errorMessage = 'Failed to reject loan';
    }
  });
}


}
