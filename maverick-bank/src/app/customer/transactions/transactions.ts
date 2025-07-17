import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionService, Transaction } from '../../services/transaction.service';
import { AccountService } from '../../services/account';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions.html',
  styleUrls: ['./transactions.scss']
})
export class TransactionsComponent implements OnInit {
  activeTab: 'deposit' | 'withdraw' | 'transfer' = 'deposit';

  customerName = '';
  accountId = 0;
  balance = 0;
  amount = 0;
  toAccountId = 0;
  recentTxns: Transaction[] = [];
  message = '';

  constructor(
    private txnService: TransactionService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    const rawUser = localStorage.getItem('currentUser');
    if (rawUser) {
      try {
        const user = JSON.parse(rawUser);
        this.accountId = +user.accountNo;
        this.customerName = user.name || 'Customer';
        this.fetchBalance();
        this.fetchRecent();
      } catch (e) {
        alert('⚠️ Invalid user data.');
      }
    } else {
      alert('⚠️ Please login first.');
    }
  }

  setTab(tab: 'deposit' | 'withdraw' | 'transfer') {
    this.activeTab = tab;
    this.amount = 0;
    this.toAccountId = 0;
    this.message = '';
  }

  fetchBalance() {
    this.accountService.getBalance(this.accountId).subscribe({
      next: (bal) => (this.balance = +bal),
      error: () => (this.balance = 0)
    });
  }

  fetchRecent() {
    this.txnService.getTransactions(this.accountId).subscribe({
      next: (data) => (this.recentTxns = data),
      error: () => (this.recentTxns = [])
    });
  }

  onDeposit() {
    if (this.amount <= 0) {
      alert('Enter a valid amount.');
      return;
    }

    this.txnService.deposit(this.accountId, this.amount).subscribe({
      next: (msg) => {
        this.message = msg;
        this.fetchBalance();
        this.fetchRecent();
        this.amount = 0;
      },
      error: () => (this.message = '❌ Deposit failed')
    });
  }

  onWithdraw() {
    if (this.amount <= 0) {
      alert('Enter a valid amount.');
      return;
    }

    this.txnService.withdraw(this.accountId, this.amount).subscribe({
      next: (msg) => {
        this.message = msg;
        this.fetchBalance();
        this.fetchRecent();
        this.amount = 0;
      },
      error: () => (this.message = '❌ Withdrawal failed')
    });
  }

  onTransfer() {
    if (this.amount <= 0 || !this.toAccountId) {
      alert('Enter valid amount and recipient.');
      return;
    }

    this.txnService.transfer(this.accountId, this.toAccountId, this.amount).subscribe({
      next: (msg) => {
        this.message = msg;
        this.fetchBalance();
        this.fetchRecent();
        this.amount = 0;
        this.toAccountId = 0;
      },
      error: () => (this.message = '❌ Transfer failed')
    });
  }
}
