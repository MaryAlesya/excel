import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../shared/services/user.service';
import { EditUserModalComponent } from '../edit-user-modal/edit-user-modal.component';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss'],
  standalone: false
})
export class UserManagementComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'role'];
  dataSource = new MatTableDataSource<any>([]); // Initialize with an empty array
  // Mapping object for column headers to property names
  columnMapping: { [key: string]: string } = {
    Name: 'username',
    Email: 'email',
    Role: 'role'
  };
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userSerice: UserService, private dialog: MatDialog) {}
  

   ngOnInit(): void {

    //this.dataSource.paginator = this.paginator;
    //this.dataSource.sort = this.sort;
    // Define custom sorting logic
  this.dataSource.sortingDataAccessor = (item, property) => {
    switch (property) {
      case 'name':
        return item.username; // Map 'name' header to 'username' property
      case 'email':
        return item.email; // Map 'email' header to 'email' property
      case 'role':
        return item.role; // Map 'role' header to 'role' property
      default:
        return item[property]; // Default behavior for other columns
    }
  };
    this.fetchUsers(); // Fetch users from the backend
  }

  fetchUsers(): void {
    this.userSerice.getAllUsers().subscribe(
      (data) => {
        this.dataSource.data = data; // Populate the MatTableDataSource with the fetched data
         // Map the fetched data to match the column headers
        this.dataSource.data = data.map((user: any) => ({
          name: user[this.columnMapping['Name']],
          email: user[this.columnMapping['Email']],
          role: user[this.columnMapping['Role']],
          id: user.id // Keep the ID for edit/delete actions
        }));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (error) => {
        //console.error('Error fetching users:', error);
        alert('Failed to fetch users. Please try again later.');
      }
    );
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  editUser(user: any): void {
    //console.log('Edit user:', user);
  }
  
 

  handleRowAction(event: { action: string; row: any }): void {
    const { action, row } = event;

    if (action === 'edit') {
      this.openEditModal(row);
    } else if (action === 'delete') {
      this.deleteUser(row);
    }
  }

  openEditModal(row: any): void {
    const dialogRef = this.dialog.open(EditUserModalComponent, {
      width: '55vw',
      data: { ...row } // Pass the row data to the modal
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // Update the data source with the edited user
        const index = this.dataSource.data.findIndex((item) => item.id === row.id);
        if (index !== -1) {
          this.dataSource.data[index] = { ...this.dataSource.data[index], ...result };
          this.dataSource._updateChangeSubscription(); // Refresh the table
        }
      }
    });
  }

  deleteUser(row: any): void {
    this.dataSource.data = this.dataSource.data.filter((item) => item.id !== row.id);
    this.dataSource._updateChangeSubscription(); // Refresh the table
  }
}