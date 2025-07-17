import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
@Component({
  selector: 'app-manage-user',
  standalone: true,
  templateUrl: './manage-users.html',
  styleUrls: ['./manage-users.scss'],
  imports: [CommonModule, FormsModule, MatDialogModule]
})
export class ManageUserComponent implements OnInit {
  users: any[] = [];
  searchTerm: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8081/getAllAccounts').subscribe({
      next: data => this.users = data,
      error: err => console.error('Failed to fetch users', err)
    });
  }

  filteredUsers() {
    return this.users.filter(user =>
      user.accountNo.toString().includes(this.searchTerm)
    );
  }
  goToRegister() {
  this.router.navigate(['/register']);
}

  deleteUser(accountNo: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.http.delete(`http://localhost:8081/closeAccount/${accountNo}`).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.accountNo !== accountNo);
          alert('User deleted successfully');
        },
        error: () => alert('User deleted successfully')
      });
    }
  }

}
