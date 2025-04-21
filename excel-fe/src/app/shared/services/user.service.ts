import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorHandlerService } from './error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/admin'; // Replace with your backend API URL

  constructor(private http: HttpClient,
    private errorHandler: ErrorHandlerService
  ) {}

  // Method to fetch all users
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  saveUser(id:any, data:any): Observable<any[]> {
    return this.http.put<any[]>(`${this.apiUrl}/saveUser/${id}`, data).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  
}