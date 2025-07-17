// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth/login';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { username, password }).pipe(
      tap((res) => {
        if (res.token) {
          // ✅ Store JWT with standard key
          localStorage.setItem('jwtToken', res.token);

          // ✅ Normalize and store user object
          const storedUser = {
            accountNo: +res.user.username, // assuming backend uses username as accountNo
            name: res.user.name || 'Customer',
            role: res.user.role?.toUpperCase() || 'USER'
          };

          localStorage.setItem('currentUser', JSON.stringify(storedUser));
        }
      })
    );
  }

  logout(): void {
    localStorage.clear();
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  getCurrentUser(): any {
    const user = localStorage.getItem('currentUser');
    return user && user !== 'undefined' ? JSON.parse(user) : null;
  }

  getRole(): string {
    const user = this.getCurrentUser();
    return user?.role?.toUpperCase() || '';
  }

  isAuthenticated(): boolean {
    return !!this.getToken(); // ✅ This is used by AuthGuard
  }
}
