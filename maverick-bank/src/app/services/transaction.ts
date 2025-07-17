import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl = 'http://localhost:8081'; // Update with your backend URL

  constructor(private http: HttpClient) {}

  deposit(accountNo: number, amount: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/deposit`, { accountNo, amount });
  }

  withdraw(accountNo: number, amount: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/withdraw`, { accountNo, amount });
  }

  transferFunds(fromAccountNo: number, toAccountNo: number, amount: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/transferFunds`, { fromAccountNo, toAccountNo, amount });
  }

  phonePeTransfer(fromAccountNo: number, toAccountNo: number, amount: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/transfer`, { fromAccountNo, toAccountNo, amount });
  }

  getMiniStatement(accountNo: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/miniStatement/${accountNo}`);
  }
}