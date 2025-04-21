import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators'; // Import tap
import { Router } from '@angular/router';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpHeaders,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private cookieService: CookieService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.body instanceof FormData) {
      const headers = new HttpHeaders({
        Authorization: 'Bearer ' + this.cookieService.get('token')
      });
      const cloneReq = req.clone({ headers });
      return next.handle(cloneReq).pipe(
        tap(
          (event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
              if (event.status === 403) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (event.status === 412) {
                this.router.navigateByUrl('/sessionopen');
              }
            }
          },
          (err: any) => {
            if (err instanceof HttpErrorResponse) {
              if (err.status === 403) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (err.status === 401) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (err.status === 412) {
                this.router.navigateByUrl('/sessionopen');
              }
            }
          }
        )
      );
    } else {
      const headers = new HttpHeaders({
        Authorization: 'Bearer ' + this.cookieService.get('token'),
        'Content-Type': 'application/json'
      });
      const cloneReq = req.clone({ headers });
      return next.handle(cloneReq).pipe(
        tap(
          (event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
              if (event.status === 403) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (event.status === 412) {
                this.router.navigateByUrl('/sessionopen');
              }
            }
          },
          (err: any) => {
            if (err instanceof HttpErrorResponse) {
              if (err.status === 403) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (err.status === 401) {
                this.router.navigateByUrl('/unauthorizedAccess');
              } else if (err.status === 412) {
                this.router.navigateByUrl('/sessionOpen');
              }
            }
          }
        )
      );
    }
  }
}