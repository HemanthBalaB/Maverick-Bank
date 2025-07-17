import { Routes } from '@angular/router';

export const customerRoutes: Routes = [
  // Redirect bare /customer to /customer/dashboard
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // 1️⃣ Dashboard
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./dashboard/dashboard').then(m => m.CustomerDashboardComponent)
  },

  // 2️⃣ Transactions (one component with three tabs)
  {
    path: 'transactions',
    loadComponent: () =>
      import('./transactions/transactions').then(m => m.TransactionsComponent)
  },

  // 3️⃣ Loans (Apply & View)
  {
    path: 'loans/apply-loan',
    loadComponent: () =>
      import('./apply-loan/apply-loan').then(m => m.ApplyLoan)
  },
  {
    path: 'loans/my-loans',
    loadComponent: () =>
      import('./my-loans/my-loans.component').then(m => m.MyLoansComponent)
  },

  // 4️⃣ Reports (Account Statement & Full Transaction History)
  {
    path: 'reports/statement',
    loadComponent: () =>
      import('../report/statement/statement').then(m => m.Statement)
  },
  {
  path: 'reports/transaction-report',
  loadComponent: () =>
    import('./transaction-report/transaction-report')
      .then(m => m.TransactionReportComponent)
},

  {
  path: 'my-accounts',
  loadComponent: () =>
    import('./my-accounts/my-accounts').then(m => m.MyAccountsComponent)
}

];
