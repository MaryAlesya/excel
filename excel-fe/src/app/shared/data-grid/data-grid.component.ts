import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { EditUserModalComponent } from '../../edit-user-modal/edit-user-modal.component';

@Component({
  selector: 'app-data-grid',
  templateUrl: './data-grid.component.html',
  styleUrls: ['./data-grid.component.scss'],
  standalone: false
})
export class DataGridComponent {
  @Input() displayedColumns: string[] = []; // Columns to display
  @Input() dataSource = new MatTableDataSource<any>([]); // Data source for the grid
  @Input() title: string = ''; // Title for the grid
  @Input() enableSearch: boolean = true; // Enable/disable search functionality
  @Input() actions: string[] = []; // Actions to display (e.g., ['edit', 'delete'])
  @Output() rowAction = new EventEmitter<{ action: string; row: any }>();// Emit events for row actions (e.g., save, edit)
  @Input() paginator!: MatPaginator; // Paginator passed from the parent
  @Input() sort!: MatSort; // Paginator passed from the parent



    constructor(private dialog: MatDialog) {
   
    }
  
    ngOnInit(): void {
     
    }


  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator; // Assign paginator to the data source
    this.dataSource.sort = this.sort; // Assign sort to the data source
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  onAction(action: string, row: any): void {
    this.rowAction.emit({ action, row }); // Emit the action and row data
  }

  onEdit(row: any): void {
    const dialogRef = this.dialog.open(EditUserModalComponent, {
      width: '400px', // Set the modal width
      data: { ...row } // Pass the row data to the modal
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //console.log('Updated Data:', result);
        // Emit the updated data to the parent component
        this.rowAction.emit({ action: 'edit', row: result });
      }
    });
  }
}