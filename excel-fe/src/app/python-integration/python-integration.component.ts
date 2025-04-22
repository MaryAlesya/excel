import { Component } from '@angular/core'; 
import { HttpClient, HttpEventType } from '@angular/common/http';

@Component(
  {
  selector: 'app-python-integration',
  templateUrl: './python-integration.component.html',
  styleUrl: './python-integration.component.scss',
  standalone: false
})
export class PythonIntegrationComponent {
  message: string = '';
  response: any = null; 
  selectedFile!: File | null; 
  uploadProgress: number = 0; 
  

  constructor(private http: HttpClient) {}

  
onFileSelected(event: Event): void { 
  const element = event.currentTarget as HTMLInputElement; 
  this.selectedFile = (element.files && element.files.length > 0) ? element.files[0] : null; 
}

  runScript() { 
    const payload = { message: this.message }; 
    this.http.post('http://localhost:5001/run-script', payload) .subscribe(
      { next: (res) => this.response = res, 
        error: (err) => this.response = { error: true, message: err.message 

        } 
      }); 
    } 

    onSubmit(): void { 
      if (!this.selectedFile) { 
        this.message = 'Please select a file.'; 
        return; 
      }
      const formData = new FormData();
formData.append('file', this.selectedFile);

this.http.post<any>('http://localhost:5001/upload', formData, {
  reportProgress: true,
  observe: 'events'
}).subscribe({
  next: event => {
    if (event.type === HttpEventType.UploadProgress && event.total) {
      this.uploadProgress = Math.round((100 * event.loaded) / event.total);
    } else if (event.type === HttpEventType.Response) {
      this.message = `Success! File saved as ${event.body.savedFilename}`;
    }
  },
  error: error => {
    this.message = `Upload failed: ${error.error?.message || error.message}`;
  }
});
}
}

