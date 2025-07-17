import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

interface Transaction {
  transactionId: number;
  type: string;
  amount: number;
  description: string;
  timestamp: string;
}

@Component({
  selector: 'app-transaction-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-report.html',
  styleUrls: ['./transaction-report.scss']
})
export class TransactionReportComponent implements OnInit {
  accountNo = 0;
  miniStatement: Transaction[] = [];
  fromDate = '';
  toDate = '';
  errorMessage = '';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    this.accountNo = Number(user?.accountNo || 0);

    if (!this.accountNo) {
      this.errorMessage = 'Account number not found.';
      return;
    }

    this.loadMiniStatement();
  }

  loadMiniStatement() {
    this.http
      .get<Transaction[]>(`http://localhost:8081/api/transactions/recent/${this.accountNo}`)
      .subscribe({
        next: (data) => this.miniStatement = data ?? [],
        error: () => this.miniStatement = []
      });
  }

  downloadStatementPdf() {
    if (!this.fromDate || !this.toDate) {
      alert('Please select both From and To dates');
      return;
    }

    const url = `http://localhost:8081/api/transactions/statement/${this.accountNo}?from=${this.fromDate}&to=${this.toDate}`;

    this.http.get(url, { responseType: 'blob' }).subscribe(blob => {
      const urlBlob = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = urlBlob;
      link.download = `statement_${this.accountNo}_${this.fromDate}_to_${this.toDate}.pdf`;
      link.click();
    }, error => {
      alert('❌ Failed to download statement');
    });
  }
}
