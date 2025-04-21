import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {
  private baseUrl = 'http://localhost:8080/api/admin/excel';

  constructor(private http: HttpClient) {}

  downloadFile(id:any,filename: string) {
    return this.http.get(`${this.baseUrl}/download/${id}/${filename}`, {
      responseType: 'blob', // Important for binary file
      observe: 'response' // To access headers if needed
    });
  }
}