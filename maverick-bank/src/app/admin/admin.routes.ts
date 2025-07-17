import { Routes } from '@angular/router';
import { AdminDashboardComponent } from './dashboard/dashboard';
import { ManageUserComponent } from './account-management/manage-users/manage-users';
import { ManageEmployeeComponent } from './account-management/manage-employees/manage-employees'; // ✅ Make sure path is correct
import { RegisterEmployeeComponent } from './account-management/register-employee/register-employee';

export const adminRoutes: Routes = [
  {
    path: 'dashboard',
    component: AdminDashboardComponent,
    children: [
      { path: 'manage-user', component: ManageUserComponent },
      { path: 'manage-employee', component: ManageEmployeeComponent }, // ✅ Added manage-employee route
      { path: '', redirectTo: 'manage-user', pathMatch: 'full' }  ,
        { path: 'add-employee', component: RegisterEmployeeComponent }, // ✅ Add this route
     // default child route
    ]
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }              // default /admin redirects to dashboard
];
