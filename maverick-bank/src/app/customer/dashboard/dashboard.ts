import { Component, OnInit } from '@angular/core';
import { CommonModule }     from '@angular/common';
import { HttpClient }       from '@angular/common/http';
import { AuthService }      from '../../services/auth.service';

/* ——— simple client-side models ——— */
/* ——— simple client-side models ——— */
interface Account {
  accountNo: number;
  name:      string;
  ifscCode:  string;
  balance:   number;
  branch: {
    branchId:   number;
    branchName: string;
  };
}

interface Transaction {
  date:        string;
  description: string;
  amount:      number;
  type:        'Deposit' | 'Withdrawal' | string;
}

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl:'./dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class CustomerDashboardComponent implements OnInit {
  account: Account | null = null;
  transactions: Transaction[] = [];
  showBalance = false;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    const accNo = +(user?.accountNo ?? user?.username ?? 0);

    if (!accNo) {
      this.errorMessage = 'No account number found!';
      return;
    }

    this.fetchAccountDetails(accNo);
    this.fetchRecentTransactions(accNo);
  }

  /* ——— API calls ——— */
  private fetchAccountDetails(accountNo: number): void {
    this.http
      .get<Account>(`http://localhost:8081/searchAccount/${accountNo}`)
      .subscribe({
        next: (data) => (this.account = data),
        error: ()   => (this.errorMessage = 'Unable to load account details')
      });
  }

  private fetchRecentTransactions(accountNo: number): void {
    this.http
      .get<Transaction[]>(`http://localhost:8081/api/transactions/recent/${accountNo}`)
      .subscribe({
        next: (data) => (this.transactions = data ?? []),
        error: ()   => (this.transactions = [])
      });
  }

  toggleBalance(): void {
    this.showBalance = !this.showBalance;
  }
}
