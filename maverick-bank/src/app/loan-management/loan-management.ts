import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface LoanApplication {
  id: number;
  accountNo: number;
  customerName: string;
  loanType: string;
  amount: number;
  durationMonths: number;
  status: string;
  appliedDate: string;
}

@Component({
  selector: 'app-loan-management',
  templateUrl: './loan-management.html',
  styleUrls: ['./loan-management.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class LoanManagementComponent implements OnInit {
  loans: LoanApplication[] = [];
  filteredLoans: LoanApplication[] = [];
  isLoading = true;
  errorMessage = '';
  searchTerm = '';
  statusFilter = 'ALL';
  currentPage = 1;
  itemsPerPage = 10;

  // Status options for filter dropdown
  statusOptions = [
    { value: 'ALL', label: 'All Statuses' },
    { value: 'PENDING', label: 'Pending' },
    { value: 'APPROVED', label: 'Approved' },
    { value: 'REJECTED', label: 'Rejected' },
    { value: 'PAID', label: 'Paid' }
  ];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchAllLoans();
  }

  fetchAllLoans(): void {
    this.isLoading = true;
    this.http.get<LoanApplication[]>('http://localhost:8081/allLoanApplications')
      .subscribe({
        next: (loans) => {
          this.loans = loans;
          this.filterLoans();
          this.isLoading = false;
        },
        error: (error) => {
          this.errorMessage = error.error || 'Failed to fetch loans';
          this.isLoading = false;
        }
      });
  }

  filterLoans(): void {
    this.filteredLoans = this.loans.filter(loan => {
      const matchesSearch = loan.customerName?.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
                          loan.accountNo.toString().includes(this.searchTerm);
      const matchesStatus = this.statusFilter === 'ALL' || loan.status === this.statusFilter;
      return matchesSearch && matchesStatus;
    });
    this.currentPage = 1; // Reset to first page when filtering
  }

  updateLoanStatus(loanId: number, newStatus: string): void {
    this.http.put('http://localhost:8081/loans/updateStatus', {
      loanId,
      status: newStatus
    }).subscribe({
      next: () => {
        const loan = this.loans.find(l => l.id === loanId);
        if (loan) {
          loan.status = newStatus;
          this.filterLoans();
        }
      },
      error: (error) => {
        console.error('Failed to update loan status', error);
      }
    });
  }

  get paginatedLoans(): LoanApplication[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredLoans.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredLoans.length / this.itemsPerPage);
  }

  changePage(page: number): void {
    this.currentPage = page;
  }

  getStatusClass(status: string): string {
    switch(status.toUpperCase()) {
      case 'APPROVED': return 'status-approved';
      case 'PENDING': return 'status-pending';
      case 'REJECTED': return 'status-rejected';
      case 'PAID': return 'status-paid';
      default: return '';
    }
  }
}