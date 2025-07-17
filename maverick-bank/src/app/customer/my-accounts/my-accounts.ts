import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-my-accounts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-accounts.html',
  styleUrls: ['./my-accounts.scss'],
  providers: [DatePipe]
})
export class MyAccountsComponent implements OnInit {
  account: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
  const user = JSON.parse(localStorage.getItem('currentUser') || '{}');
  const role = user?.role?.toLowerCase(); // normalize
  const accountNo = user?.accountNo;

  if (accountNo && role === 'user') {
    this.fetchAccount(accountNo);
  } else {
    console.error('Invalid user or role');
    window.location.href = '/login';
  }
}

  fetchAccount(accountNo: number): void {
    this.http.get<any>(`http://localhost:8081/searchAccount/${accountNo}`)
      .subscribe({
        next: data => this.account = data,
        error: err => {
          console.error('Failed to fetch account', err);
          window.location.href = '/login'; // fallback
        }
      });
  }

  downloadPdf(): void {
    const accountNo = this.account?.accountNo;
    if (!accountNo) return;

    const url = `http://localhost:8081/api/transactions/statement/${accountNo}`;
    window.open(url, '_blank');
  }
}
