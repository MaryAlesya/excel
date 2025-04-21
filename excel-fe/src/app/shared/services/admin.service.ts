import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorHandlerService } from './error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin'; // Replace with your backend API URL

  constructor(private http: HttpClient,
    private errorHandler: ErrorHandlerService
  ) {}

  // Method to fetch all users
  getAllUploads(type:any): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/uploads/${type}`).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  // Method to get Sales Data
  getSales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getSalesData`).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  // Method to get Purchases Data
  getPurchases(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getPurchasesData`).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  savePurchase(id:any, data:any): Observable<any[]> {
    return this.http.put<any[]>(`${this.apiUrl}/savePurchase/${id}`, data).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  saveSale(id:any, data:any): Observable<any[]> {
    return this.http.put<any[]>(`${this.apiUrl}/saveSale/${id}`, data).pipe(
      catchError(this.errorHandler.handleError) // Handle errors
    );
  }

  getDashboardStats(): Observable<any> { 
    return this.http.get(`${this.apiUrl}/dashboard`).pipe(
      catchError(this.errorHandler.handleError)); // Handle errors 
    } 
}
