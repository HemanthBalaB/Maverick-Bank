// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login';
import { RegisterComponent } from './auth/register/register';
import { EmployeeLayoutComponent } from './employee/layout/employee-layout.component';
import { customerRoutes } from './customer/customer.routes';
import { EMPLOYEE_ROUTES } from './employee/employee.routes';
import { adminRoutes } from './admin/admin.routes';

export const appRoutes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'customer',
    loadComponent: () =>
      import('./customer/layout/layout').then(m => m.CustomerLayoutComponent),
    children: customerRoutes
  },

  {
    path: 'employee',
    component: EmployeeLayoutComponent,
    children: EMPLOYEE_ROUTES
  },

  {
    path: 'admin',
    children: adminRoutes // ✅ Correct way to use nested admin dashboard + manage-user etc.
  }
];
