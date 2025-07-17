import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Transaction {
  transactionId: number;
  accountNo:     number;
  type:          string;
  amount:        number;
  description:   string;
  timestamp:     string;
}

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private base = 'http://localhost:8081/api/transactions';

  constructor(private http: HttpClient) {}

  getTransactions(accountNo: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.base}/recent/${accountNo}`);
  }

  deposit(accountNo: number, amount: number): Observable<string> {
    return this.http.post(`${this.base}/deposit`, {
      accountNo, amount
    }, { responseType: 'text' });
  }

  withdraw(accountNo: number, amount: number): Observable<string> {
    return this.http.post(`${this.base}/withdraw`, {
      accountNo, amount
    }, { responseType: 'text' });
  }

  transfer(fromAccountNo: number, toAccountNo: number, amount: number): Observable<string> {
    return this.http.post(`${this.base}/transferFunds`, {
      fromAccountNo, toAccountNo, amount
    }, { responseType: 'text' });
  }
}
