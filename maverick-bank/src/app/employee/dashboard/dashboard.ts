// src/app/employee/dashboard/employee-dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

interface Transaction {
  accountNo: number;
  type: string;
  amount: number;
  description: string;
  date: string;
}

interface AccountDetails {
  accountNo: number;
  name: string;
  email: string;
  balance: number;
  ifscCode: string;
  mobileNo: string;
  address: string;
  // Add more fields if present
}


@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class EmployeeDashboardComponent implements OnInit {
  currentTab: string = 'accountTx';

  accountTransactions: Transaction[] = [];
  accountDetails: AccountDetails | null = null;
  accountSearch: number | null = null;
  errorMessage: string = '';
  employeeName: string = '';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.http.get<any>('http://localhost:8081/api/employees/session-check', {
      withCredentials: true
    }).subscribe({
      next: (employee) => {
        this.employeeName = employee.name;
      },
      error: () => {
        location.href = '/login';
      }
    });
  }

  fetchAccountTransactions() {
    if (!this.accountSearch) return;

    // Reset before new fetch
    this.accountDetails = null;
    this.accountTransactions = [];
    this.errorMessage = '';

    // 🟢 1. Fetch Account Details
    this.http.get<AccountDetails>(`http://localhost:8081/searchAccount/${this.accountSearch}`, {
      withCredentials: true
    }).subscribe({
      next: account => this.accountDetails = account,
      error: () => this.errorMessage = 'Failed to load account details'
    });

    // 🟢 2. Fetch Transactions
    this.http.get<Transaction[]>(`http://localhost:8081/api/transactions/recent/${this.accountSearch}`, {
      withCredentials: true
    }).subscribe({
      next: tx => this.accountTransactions = tx,
      error: () => this.errorMessage = 'Failed to load account transactions'
    });
  }

  logout() {
    this.http.get('http://localhost:8081/api/employees/logout', {
      withCredentials: true
    }).subscribe({
      next: () => location.href = '/login',
      error: () => location.href = '/login'
    });
  }
}
