import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-manage-employees',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './manage-employees.html',
  styleUrls: ['./manage-employees.scss']
})
export class ManageEmployeeComponent implements OnInit {
  employees: any[] = [];
  searchTerm: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees() {
    this.http.get<any[]>('http://localhost:8081/api/employees/all')
      .subscribe({
        next: data => this.employees = data,
        error: err => this.errorMessage = 'Failed to load employees'
      });
  }

  filteredEmployees() {
    if (!this.searchTerm.trim()) return this.employees;
    return this.employees.filter(emp =>
      emp.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      emp.email.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
