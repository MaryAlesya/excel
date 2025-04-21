import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  private apiUrl = 'http://localhost:8080/api/analyst/excel'; // Replace with your backend API URL

  constructor(private http: HttpClient) {}

  uploadFile(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/upload`, formData).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    //console.error('Error occurred:', error);
    return throwError(() => new Error('Failed to upload the file. Please try again.'));
  }
}