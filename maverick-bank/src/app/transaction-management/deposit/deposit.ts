import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction';
import { AuthService } from '../../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-deposit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './deposit.html',
  styleUrls: ['./deposit.scss']
})
export class DepositComponent {
  amount: number = 0;
  accountNo: number | null = null;
  message: string = '';
  isLoading: boolean = false;

  constructor(
    private transactionService: TransactionService,
    private authService: AuthService,
    private router: Router
  ) {
    this.accountNo = this.authService.getAccountNo();
  }

  deposit(): void {
    if (this.accountNo && this.amount > 0) {
      this.isLoading = true;
      this.transactionService.deposit(this.accountNo, this.amount).subscribe({
        next: (response: any) => {
          this.message = response;
          this.isLoading = false;
          setTimeout(() => this.router.navigate(['/customer/transactions']), 2000);
        },
        error: (error) => {
          this.message = error.error || 'Deposit failed';
          this.isLoading = false;
        }
      });
    } else {
      this.message = 'Please enter a valid amount';
    }
  }
}
