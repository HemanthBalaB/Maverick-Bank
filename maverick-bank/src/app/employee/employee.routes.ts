import { Routes } from '@angular/router';
import { EmployeeDashboardComponent } from './dashboard/dashboard';
import { ReviewLoansComponent } from './loans/review-loans.component';


export const EMPLOYEE_ROUTES: Routes = [
  { path: 'dashboard', component: EmployeeDashboardComponent },
  { path: 'review-loans', component: ReviewLoansComponent },
];
