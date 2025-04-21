import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DownloadService } from '../shared/services/download.service';
import { AdminService } from '../shared/services/admin.service';
import { saveAs } from 'file-saver';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-sales-management',
  templateUrl: './sales-management.component.html',
  styleUrls: ['./sales-management.component.scss'],
  standalone: false
})
export class SalesManagementComponent implements OnInit {
  // Columns for Uploads Data Grid
  uploadsDisplayedColumns: string[] = ['originalFileName', 'type','uploadedBy', 'uploadedOn'];
  uploadsDataSource = new MatTableDataSource<any>([]); // Initialize with an empty array
  salesDataSource = new MatTableDataSource<any>([]); // Initialize with an empty array
  // Options for the Score field
  scoreOptions: string[] = ['High', 'Low'];
  // Columns for Sales Data Grid
  salesDisplayedColumns: string[] = ['item', 'value1', 'value2', 'value3', 'total','average', 'score','date','actions'];
  type : string = 'Sales';
  columnFilters: { [key: string]: string } = {}; // Store filters for each column
  uniqueValues: { [key: string]: string[] } = {}; // Store unique values for each column
 

  @ViewChild('uploadsPaginator') uploadsPaginator!: MatPaginator;
  @ViewChild('salesAdminUpSort') salesAdminUpSort!: MatSort;

  @ViewChild('salesPaginator') salesPaginator!: MatPaginator;
  @ViewChild('salesDataSort') salesDataSort!: MatSort;

  columnMapping: { [key: string]: string } = {
    originalFileName: 'File Name',
    type: 'Type',
    uploadedBy: 'Uploaded By',
    uploadedOn: 'Uploaded On'
  };

  constructor(private fileDownloadService: DownloadService, private adminService: AdminService) {
    this.fetchUploads(); // Fetch uploads data on component initialization
    this.fetchSales(); // Fetch sales data on component initialization
  }

  ngOnInit(){
    // Assign paginator and sort to Uploads Data Grid
    this.uploadsDataSource.paginator = this.uploadsPaginator;
    this.uploadsDataSource.sort = this.salesAdminUpSort;
  // Assign paginator and sort to Sales Data Grid
    this.salesDataSource.paginator = this.salesPaginator;
    this.salesDataSource.sort = this.salesDataSort;
   }

  fetchUploads(): void {
    this.adminService.getAllUploads(this.type).subscribe(
      (data) => {
        this.uploadsDataSource.data = data; // Populate the MatTableDataSource with the fetched data
        this.uploadsDataSource.paginator = this.uploadsPaginator;
        this.uploadsDataSource.sort = this.salesAdminUpSort;
      },
      (error) => {
        //console.error('Error fetching Uploads:', error);
        alert('Failed to fetch uploads. Please try again later.');
      }
    );
  }

  fetchSales(): void {
    this.adminService.getSales().subscribe(
      (data) => {
        this.salesDataSource.data = data; // Populate the MatTableDataSource with the fetched data
         this.salesDataSource.data = this.salesDataSource.data.map((sale: any) => {
          return {
            ...sale,
            total: parseFloat(sale.total.toFixed(2)),
            average: parseFloat(sale.average.toFixed(2)),
            date: sale.date ? new Date(sale.date).toISOString() : null // Ensure date is valid
          };
        });
        this.salesDataSource.paginator = this.salesPaginator;
        this.salesDataSource.sort = this.salesDataSort;
        // Calculate unique values for each column
        this.calculateUniqueValues(data);
      },
      (error) => {
        //console.error('Error fetching Uploads:', error);
        alert('Failed to fetch uploads. Please try again later.');
      }
    );
  }
  applyUploadsFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.uploadsDataSource.filter = filterValue.trim().toLowerCase();
  }

  applySalesFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.salesDataSource.filter = filterValue.trim().toLowerCase();
  }
  

  onTotalChange(sale: any): void {
    const total = parseFloat(sale.total); // Convert to a number
    sale.total = sale.total?sale.total.toFixed(2) : '0.00' // Round to 2 decimal places
  }
  
  onAverageChange(sale: any): void {
    const total = parseFloat(sale.average); // Convert to a number
    sale.average = sale.average?sale.average.toFixed(2) : '0.00' // Round to 2 decimal places
  }

  onScoreChange(sale: any): void {
    //console.log(`Score updated for Item ID ${sale.id}:`, sale.score);
    // Add logic to handle the updated price (e.g., save to the server)
  }

  onUpdateRow(sale: any): void {
    //console.log(`Updating row for Product ID ${sale.id}:`, sale);
  
    this.adminService.saveSale(sale.id, sale).subscribe(
      (response) => {
        //console.log('Record updated successfully:', response);
        alert('Purchase updated successfully!');
      },
      (error) => {
        //console.error('Error updating purchase:', error);
        alert('Failed to update record. Please try again.');
      }
    );
  }


  downloadFile(id: number, filename: string): void {
    this.fileDownloadService.downloadFile(id, filename).subscribe(
      (response) => {
        const blob = new Blob([response.body as BlobPart], { type: response.headers.get('Content-Type') || 'application/octet-stream' });
        const url = window.URL.createObjectURL(blob);

        // Create a temporary anchor element to trigger the download
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        a.click();

        // Revoke the object URL to free up memory
        window.URL.revokeObjectURL(url);
      },
      (error) => {
        //console.error('File download failed:', error);
        alert('Failed to download the file. Please try again.');
      }
    );
  }

  exportSalesData(): void {
     const dataToExport = this.salesDataSource.data.map((row) => ({
         //ID: row.id,
         Item: row.item,
         Value1 : row.value1,
         Value2: row.value2, 
         Value3: row.value3,
         Total: row.total ? parseFloat(row.total.toString()).toFixed(2) : '0.00',
         Average: row.average ? parseFloat(row.average.toString()).toFixed(2) : '0.00',
         Score : row.score,
         Date: row.date
       }));
   
       const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(dataToExport);
       const workbook: XLSX.WorkBook = { Sheets: { 'Sales Data': worksheet }, SheetNames: ['Sales Data'] };
       const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
   
       const timestamp = new Date().toISOString().replace(/[-:.]/g, '').slice(0, 15);
       const blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
       saveAs(blob, `sales_${timestamp}.xlsx`);
}

applyColumnFilter(column: string, event: Event): void {
  const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  this.columnFilters[column] = filterValue; // Update the filter for the specific column

  // Combine all column filters into a single filter string
  this.salesDataSource.filter = JSON.stringify(this.columnFilters);
}

calculateUniqueValues(data: any[]): void {
  this.uniqueValues = {};
  data.forEach((row) => {
    Object.keys(row).forEach((key) => {
      if (!this.uniqueValues[key]) {
        this.uniqueValues[key] = [];
      }
      if (!this.uniqueValues[key].includes(row[key])) {
        this.uniqueValues[key].push(row[key]);
      }
    });
  });
}

applyDropdownFilter(column: string, value: string): void {
  this.columnFilters[column] = value; // Update the filter for the specific column

  // Combine all column filters into a single filter string
  this.salesDataSource.filter = JSON.stringify(this.columnFilters);
}

ngAfterViewInit(): void {
  this.salesFilterPredicate();
  this.uploadsFilterPredicate();
 }

 salesFilterPredicate(){
 // Custom sorting logic for mixed data types
  this.salesDataSource.sortingDataAccessor = (item, property) => {
    switch (property) {
      case 'item':
        return item.item.toLowerCase(); // Sort strings case-insensitively
      case 'value1':
        return item.value1; // Sort numbers as-is
      case 'value1':
        return item.value2;
      case 'value3':
          return item.value3;
      case 'total':
            return item.total;
      case 'average':
            return item.average;
      case 'date':
        return new Date(item.uploadedOn).getTime(); // Sort dates as timestamps
      case 'score':
        return item.type.toLowerCase(); // Sort strings case-insensitively
      default:
        return item[property]; // Default sorting for other columns
    }
  };
  this.salesDataSource.paginator = this.salesPaginator;
  this.salesDataSource.sort = this.salesDataSort;

 }

 uploadsFilterPredicate(){
   // Custom sorting logic for mixed data types
  this.salesDataSource.sortingDataAccessor = (item, property) => {
    switch (property) {
      case 'uploadedOn':
        return new Date(item.uploadedOn).getTime(); // Sort dates as timestamps
      case 'type':
        return item.type.toLowerCase(); // Sort strings case-insensitively
      case 'uploadedBy.email':
        return item.uploadedBy.email.toLowerCase(); // Sort strings case-insensitively
      case 'originalFileName':
        return item.originalFileName.toLowerCase(); // Sort strings case-insensitively
      default:
        return item[property]; // Default sorting for other columns
    }
  };
  this.uploadsDataSource.paginator = this.uploadsPaginator;
  this.uploadsDataSource.sort = this.salesAdminUpSort;
 }
}