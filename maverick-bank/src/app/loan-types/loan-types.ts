import { Component, OnInit } from '@angular/core';
import { LoanService } from '../services/loan';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loan-types',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './loan-types.html',
  styleUrls: ['./loan-types.scss']
})
export class LoanTypesComponent implements OnInit {
  loanTypes: any[] = [];
  isLoading = true;
  errorMessage = '';

  constructor(
    private loanService: LoanService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadLoanTypes();
  }

  loadLoanTypes(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.loanService.getAvailableLoanTypes().subscribe({
      next: (types: any[]) => {
        this.loanTypes = types;
        this.isLoading = false;
      },
      error: (err: any) => {
        this.errorMessage = 'Failed to load loan types';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  selectLoanType(type: any): void {
    this.router.navigate(['/apply-loan'], { 
      state: { selectedLoanType: type } 
    });
  }
}