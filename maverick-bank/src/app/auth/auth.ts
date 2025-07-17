import { Injectable } from '@angular/core';
import { AccountHolder } from '../classes/account-holder.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081'; // Directly hardcoded API URL

  constructor(private http: HttpClient) { }

  login(accountNo: number, password: string) {
    return this.http.post<AccountHolder>(`${this.apiUrl}/login`, { accountNo, password });
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  getCurrentUser(): AccountHolder | null {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
  }

  isLoggedIn(): boolean {
    return this.getCurrentUser() !== null;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user ? user.accountNo === 1001 : false; // Assuming 1001 is admin
  }
}