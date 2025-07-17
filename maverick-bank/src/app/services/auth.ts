// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError, tap } from 'rxjs';

interface LoginResponse {
  token: string;
  role: string;
  user: any; // Consider creating a proper User interface
  accountNo?: number;
}

interface ErrorDetails {
  message: string;
  details: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8081';
  private loginUrl = `${this.baseUrl}/auth/login`;

  constructor(private http: HttpClient) {}

  login(username: number, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.loginUrl, { username, password }).pipe(
      tap((response: LoginResponse) => {
        this.storeAuthData(response, username);
      }),
      catchError((error: HttpErrorResponse) => {
        return throwError(() => this.getErrorDetails(error));
      })
    );
  }

  private storeAuthData(response: LoginResponse, username: number): void {
    if (response?.token) {
      localStorage.setItem('jwtToken', response.token);
      localStorage.setItem('userRole', response.role);
      localStorage.setItem('accountNo', username.toString());
      
      if (response.user) {
        localStorage.setItem('currentUser', JSON.stringify(response.user));
      } else {
        // Create minimal user object if not provided
        const minimalUser = {
          accountNo: username,
          role: response.role
        };
        localStorage.setItem('currentUser', JSON.stringify(minimalUser));
      }
    }
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  getAccountNo(): number | null {
    const accStr = localStorage.getItem('accountNo');
    return accStr ? parseInt(accStr, 10) : null;
  }

  getRole(): string | null {
    return localStorage.getItem('userRole');
  }

  getCurrentUser(): any {
    const userStr = localStorage.getItem('currentUser');
    try {
      return userStr ? JSON.parse(userStr) : null;
    } catch (err) {
      console.error('Error parsing user data:', err);
      this.clearAuthData();
      return null;
    }
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }

  logout(): void {
    this.clearAuthData();
  }

  private clearAuthData(): void {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('accountNo');
    localStorage.removeItem('currentUser');
  }

  private getErrorDetails(error: HttpErrorResponse): ErrorDetails {
    if (error.status === 0) {
      return {
        message: 'Network error',
        details: [
          'Could not connect to server',
          'Check if backend is running',
          'Verify CORS is properly configured'
        ]
      };
    }

    const defaultMessage = error.error?.message || 'Login failed';
    const details = [error.message];

    if (error.status === 401) {
      return {
        message: 'Authentication failed',
        details: ['Invalid credentials', ...details]
      };
    }

    if (error.status >= 500) {
      return {
        message: 'Server error',
        details: ['Please try again later', ...details]
      };
    }

    return {
      message: defaultMessage,
      details
    };
  }
}