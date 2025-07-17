// loan.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = 'http://localhost:8081/api/loans';

  constructor(private http: HttpClient) {}

  getUserLoans() {
    return this.http.get<any[]>(`${this.apiUrl}/user`);
  }
  payLoan(loanId: number) {
  return this.http.post<string>(`${this.apiUrl}/pay/${loanId}`, {});
}

}