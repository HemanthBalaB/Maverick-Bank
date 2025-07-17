import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountHolder } from '../classes/account-holder.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8081'; // Base API URL

  constructor(private http: HttpClient) {}

  /** Create a new account */
  createAccount(account: AccountHolder) {
    return this.http.post<AccountHolder>(`${this.apiUrl}/createAccount`, account);
  }

  /** Get a single account by account number */
  getAccount(accountNo: number): Observable<AccountHolder> {
    return this.http.get<AccountHolder>(`${this.apiUrl}/searchAccount/${accountNo}`);
  }

  /** Update account details */
  updateAccount(account: AccountHolder): Observable<AccountHolder> {
    return this.http.put<AccountHolder>(`${this.apiUrl}/updateAccount`, account);
  }

  /** Get all accounts */
  getAllAccounts(): Observable<AccountHolder[]> {
    return this.http.get<AccountHolder[]>(`${this.apiUrl}/getAllAccounts`);
  }

  /** ✅ Get current balance */
  getBalance(accountNo: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getBalance/${accountNo}`);
  }
}
