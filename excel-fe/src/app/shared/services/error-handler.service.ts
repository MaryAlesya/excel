import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {
  handleError(error: HttpErrorResponse): Observable<never> {
    //console.error('An error occurred:', error); // Log the error to the console
    let errorMessage = 'An unknown error occurred. Please try again later.';

    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Server-side error: ${error.status} - ${error.message}`;
    }

    return throwError(() => new Error(errorMessage));
  }
}