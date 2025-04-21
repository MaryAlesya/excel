import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { DownloadService } from '../shared/services/download.service';
import { parse } from 'path';
import { AdminService } from '../shared/services/admin.service';

@Component({
  selector: 'app-purchase-management',
  templateUrl: './purchase-management.component.html',
  styleUrls: ['./purchase-management.component.scss'],
  standalone: false
})
export class PurchaseManagementComponent implements OnInit {
  // Columns for Uploads Data Grid
  uploadsDisplayedColumns: string[] = ['originalFileName', 'type','uploadedBy', 'uploadedOn'];
 
  purchaseDisplayedColumns: string[] = ['item', 'value1', 'value2', 'value3', 'total','average', 'score','date','actions'];
 
  uploadsDataSource = new MatTableDataSource<any>([]); // Initialize with an empty array
  purchaseDataSource = new MatTableDataSource<any>([]); 

  @ViewChild('uploadsPaginator') uploadsPaginator!: MatPaginator;
  @ViewChild('purchaseAdminUpsort') purchaseAdminUpsort!: MatSort;

  @ViewChild('purchasePaginator') purchasePaginator!: MatPaginator;
  @ViewChild('purchaseDataSort') purchaseDataSort!: MatSort;

   // Options for the Score field
   scoreOptions: string[] = ['High', 'Low'];
   type : string = 'purchases';

   columnMapping: { [key: string]: string } = {
    originalFileName: 'File Name',
    type: 'Type',
    uploadedBy: 'Uploaded By',
    uploadedOn: 'Uploaded On'
  };

  constructor(private fileDownloadService: DownloadService,private adminService: AdminService) {
    this.fetchUploads(); // Fetch uploads data on component initialization
    this.fetchPurchases(); // Fetch sales data on component initialization
  }

  ngOnInit(): void {
    // Assign paginator and sort to Uploads Data Grid
    this.uploadsDataSource.paginator = this.uploadsPaginator;
    this.uploadsDataSource.sort = this.purchaseAdminUpsort;

    // Assign paginator and sort to Purchase Data Grid
    this.purchaseDataSource.paginator = this.purchasePaginator;
    this.purchaseDataSource.sort = this.purchaseDataSort;
}

  applyUploadsFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.uploadsDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyPurchaseFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.purchaseDataSource.filter = filterValue; // Set the filter directly as a string
  }

 

  onUpdateRow(purchase: any): void {
    //console.log(`Updating row for Product ID ${purchase.id}:`, purchase);
  
    this.adminService.savePurchase(purchase.id, purchase).subscribe(
      (response) => {
        //console.log('Record updated successfully:', response);
        alert('Record updated successfully!');
      },
      (error) => {
        //console.error('Error updating record:', error);
        alert('Failed to update purchase. Please try again.');
      }
    );
  }

  exportPurchaseData(): void {
    const dataToExport = this.purchaseDataSource.data.map((row) => ({
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
    const workbook: XLSX.WorkBook = { Sheets: { 'Purchase Data': worksheet }, SheetNames: ['Purchase Data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    const timestamp = new Date().toISOString().replace(/[-:.]/g, '').slice(0, 15);
    const blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
    saveAs(blob, `purchase_${timestamp}.xlsx`);
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

  onTotalChange(purchase: any): void {
    const total = parseFloat(purchase.total); // Convert to a number
    purchase.total = purchase.total?purchase.total.toFixed(2) : '0.00' // Round to 2 decimal places
  }
  
  onAverageChange(purchase: any): void {
    const total = parseFloat(purchase.average); // Convert to a number
    purchase.average = purchase.average?purchase.average.toFixed(2) : '0.00' // Round to 2 decimal places
  }

  onScoreChange(sale: any): void {
    //console.log(`Score updated for Item ID ${sale.id}:`, sale.score);
    // Add logic to handle the updated price (e.g., save to the server)
  }

  fetchUploads(): void {
    this.adminService.getAllUploads(this.type).subscribe(
      (data) => {
        this.uploadsDataSource.data = data; // Populate the MatTableDataSource with the fetched data
        this.uploadsDataSource.paginator = this.uploadsPaginator;
        this.uploadsDataSource.sort = this.purchaseAdminUpsort;
      },
      (error) => {
        //console.error('Error fetching Uploads:', error);
        alert('Failed to fetch uploads. Please try again later.');
      }
    );
  }

  fetchPurchases(): void {
    this.adminService.getPurchases().subscribe(
      (data) => {
        this.purchaseDataSource.data = data; // Populate the MatTableDataSource with the fetched data
        
        this.purchaseDataSource.data = this.purchaseDataSource.data.map((purchase: any) => {
          return {
            ...purchase,
            total: parseFloat(purchase.total.toFixed(2)),
            average: parseFloat(purchase.average.toFixed(2)),
            date: purchase.date ? new Date(purchase.date).toISOString() : null // Ensure date is valid
          };
        });
        this.purchaseDataSource.paginator = this.purchasePaginator;
        this.purchaseDataSource.sort = this.purchaseDataSort;
       },
      (error) => {
        //console.error('Error fetching Uploads:', error);
        alert('Failed to fetch uploads. Please try again later.');
      }
    );
  }
  ngAfterViewInit(): void {
    this.purchaseFilterPredicate();
    this.uploadsFilterPredicate();
   }
  
   purchaseFilterPredicate(){
   // Custom sorting logic for mixed data types
    this.purchaseDataSource.sortingDataAccessor = (item, property) => {
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
    this.purchaseDataSource.paginator = this.purchasePaginator;
    this.purchaseDataSource.sort = this.purchaseDataSort;
  
   }
  
   uploadsFilterPredicate(){
     // Custom sorting logic for mixed data types
    this.purchaseDataSource.sortingDataAccessor = (item, property) => {
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
    this.uploadsDataSource.sort = this.purchaseAdminUpsort;
   }
}