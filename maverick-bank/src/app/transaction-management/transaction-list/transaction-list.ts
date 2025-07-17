import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../services/transaction';
import { AuthService } from '../../services/auth';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  providers: [DatePipe],
  templateUrl: './transaction-list.html',
  styleUrls: ['./transaction-list.scss']
})
export class TransactionListComponent implements OnInit {
  transactions: any[] = [];
  accountNo: number | null = null;

  constructor(
    private transactionService: TransactionService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.accountNo = this.authService.getAccountNo();
    if (this.accountNo) {
      this.loadTransactions();
    }
  }

  loadTransactions(): void {
    if (this.accountNo) {
      this.transactionService.getMiniStatement(this.accountNo).subscribe({
        next: (data) => {
          this.transactions = data;
        },
        error: (err) => {
          console.error('Error fetching transactions:', err);
        }
      });
    }
  }
}
