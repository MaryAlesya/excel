import { Component, ViewChild } from '@angular/core';
import * as XLSX from 'xlsx';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UploadService } from '../shared/services/upload.service';
import { CookieService } from 'ngx-cookie-service';
import { AnalystService } from '../shared/services/analyst.service';
import { DownloadService } from '../shared/services/download.service';

@Component({
  selector: 'app-analyst-sales',
  templateUrl: './analyst-sales.component.html',
  styleUrls: ['./analyst-sales.component.scss'],
  standalone: false
})
export class AnalystSalesComponent {
  displayedColumns: string[] = ['originalFileName', 'type','uploadedBy', 'uploadedOn'];
  dataSource = new MatTableDataSource<any>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild('salesUploadSort') salesUploadSort!: MatSort;
  errorMessage: string | null = null;
  selectedFile: File | null = null; // Store the selected file
  userId: string | null = null;
  type : string = 'Sales';

  // Define the expected schema and data types
  expectedSchema = [
    { column: 'Item', type: 'string' },
    { column: 'Value1', type: 'number' },
    { column: 'Value2', type: 'number' },
    { column: 'Value3', type: 'number' },
    { column: 'Date', type: 'date' }
  ];

  columnMapping: { [key: string]: string } = {
    originalFileName: 'File Name',
    type: 'Type',
    uploadedBy: 'Uploaded By',
    uploadedOn: 'Uploaded On'
  };

  constructor(private fileUploadService: UploadService, 
    private cookieService: CookieService,
    private analystService: AnalystService,
    private fileDownloadService: DownloadService) {
     // Retrieve the user_id from the cookie
     this.userId = this.cookieService.get('user_id');

     //console.log('Logged-in User ID:', this.userId);
     this.fetchUploads(this.userId, this.type); // Fetch uploads data on component initialization
  }

  ngAfterViewInit(): void {
    //console.log('Sort ngAfter View Init:', this.salesUploadSort);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.salesUploadSort;

    // Custom sorting logic for mixed data types
    this.dataSource.sortingDataAccessor = (item, property) => {
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
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0]; // Store the selected file
    this.errorMessage = null; // Clear any previous error messages
  }

  onUpload(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a file before uploading.';
      return;
    }

    const file = this.selectedFile;
    const reader = new FileReader();
    reader.onload = (e: any) => {
      const data = new Uint8Array(e.target.result);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[0];
      const sheetData = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], { header: 1 });

      // Validate the schema
      const headers = sheetData[0] as string[]; // First row contains the headers
      if (this.validateSchema(headers)) {
        // If schema is valid, process the data
        const rows = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
        const validationResult = this.validateDataTypes(rows);

        if (validationResult.isValid) {
          // Prepare the file for upload
          const formData = new FormData();
          formData.append('file', file);
          formData.append('sheet', 'Sales'); 
          formData.append('userId', this.userId || ''); // Append user ID to the form data

          // Call the service to upload the file
          this.fileUploadService.uploadFile(formData).subscribe(
            (response: any) => {
              //console.log('File uploaded successfully:', response);
              alert('File uploaded successfully!');
              this.fetchUploads(this.userId, this.type); // Fetch the updated uploads data
              // Process the response data if needed
              if (response.data) {
                this.dataSource.data = response.data.map((row: any, index: number) => ({
                  id: index + 1,
                  fileName: file.name,
                  uploadedBy: 'Analyst', // Replace with actual user info if available
                  uploadedOn: new Date().toLocaleString(),
                  ...row
                }));
              }
              this.errorMessage = null; // Clear any previous error messages
            },
            (error) => {
              //console.error('Error uploading file:', error);
              this.errorMessage = 'Failed to upload the file. Please try again.';
            }
          );
        } else {
          // If data type validation fails, show an error message
          this.errorMessage = `Invalid data types in the uploaded file. Errors: ${validationResult.errors.join(
            ', '
          )}`;
          //this.dataSource.data = []; // Clear the data grid
        }
      } else {
        // If schema is invalid, show an error message
        this.errorMessage = `Invalid file schema. Expected columns: ${this.expectedSchema
          .map((col) => col.column)
          .join(', ')}`;
        this.dataSource.data = []; // Clear the data grid
      }
    };
    reader.readAsArrayBuffer(file);
  }

  validateSchema(headers: string[]): boolean {
    // Check if all expected columns are present in the uploaded file
    return this.expectedSchema.every((col) => headers.includes(col.column));
  }

  validateDataTypes(rows: any[]): { isValid: boolean; errors: string[] } {
    const errors: string[] = [];

    rows.forEach((row, rowIndex) => {
      this.expectedSchema.forEach((col) => {
        const value = row[col.column];
        if (!this.isValidType(value, col.type)) {
          errors.push(`Row ${rowIndex + 1}, Column "${col.column}" should be of type ${col.type}`);
        }
      });
    });

    return { isValid: errors.length === 0, errors };
  }

  isValidType(value: any, type: string): boolean {
    if (type === 'string') {
      return typeof value === 'string';
    } else if (type === 'number') {
      return !isNaN(value) && typeof value === 'number';
    } else if (type === 'date') {
      return !isNaN(Date.parse(value));
    }
    return false;
  }

  fetchUploads(id:any, type:any): void {
    this.analystService.getuploads(this.userId, this.type).subscribe(
      (data) => {
        this.dataSource.data = data; // Populate the MatTableDataSource with the fetched data
        this.dataSource.data = data.map((item: any) => ({
          originalFileName: item.originalFileName || 'Unknown',
          type: item.type || 'Unknown',
          uploadedBy: item.uploadedBy || 'Unknown',
          uploadedOn: item.uploadedOn ? new Date(item.uploadedOn).toISOString() : 'N/A'
        }));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.salesUploadSort;
      },
      (error) => {
        //console.error('Error fetching Uploads:', error);
        alert('Failed to fetch uploads. Please try again later.');
      }
    );
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
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
}