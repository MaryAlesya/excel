import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AnalystService {
  private apiUrl = 'http://localhost:8080/api/analyst'; // Replace with your backend API URL

  constructor(private http: HttpClient) {}

  // Method to fetch all users
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Error handling
  private handleError(error: HttpErrorResponse): Observable<never> {
    //console.error('Error fetching users:', error);
    return throwError(() => new Error('Failed to fetch users. Please try again later.'));
  }

 // Method to fetch uploads
  getuploads(id:any, type:any): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getUploads/${id}/${type}`).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  

  
}