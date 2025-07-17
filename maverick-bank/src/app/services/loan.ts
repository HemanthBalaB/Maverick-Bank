import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  constructor(private http: HttpClient) {}

  getCustomerLoans(accountNo: string): Observable<any[]> {
    return this.http.get<any[]>(`/api/loans/${accountNo}`);
  }

  makePayment(loanId: string, accountNo: string, amount: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>('/api/loans/payment', {
      loanId,
      accountNo,
      amount
    });
  }

  getAvailableLoanTypes(): Observable<any[]> {
    return this.http.get<any[]>('/api/loan-types');
  }
}