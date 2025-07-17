import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

interface LoanApplication {
  loanId: number;
  loanAmount: number;
  tenureInMonths: number;
  purpose: string;
  status: string;
  appliedDate: string;
}

@Component({
  selector: 'app-my-loans',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-loans.component.html',
  styleUrls: ['./my-loans.component.scss']
})
export class MyLoansComponent implements OnInit {
  loans: LoanApplication[] = [];
  errorMessage = '';
  loading = false;

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    const accountNo = +(user?.accountNo ?? user?.username ?? 0);

    if (!accountNo) {
      this.errorMessage = 'No account number found!';
      return;
    }

    this.loading = true;

    this.http.get<LoanApplication[]>(`http://localhost:8081/api/loans/status/${accountNo}`)
      .subscribe({
        next: data => {
          this.loans = data ?? [];
          this.loading = false;
        },
        error: () => {
          this.errorMessage = 'Unable to fetch loan records';
          this.loading = false;
        }
      });
  }
}
