import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AuthService } from './shared/services/auth.service';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { NgChartsModule } from 'ng2-charts';
import { UserManagementComponent } from './user-management/user-management.component';
import { LeftNavComponent } from './shared/left-nav/left-nav.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatNativeDateModule } from '@angular/material/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatStepperModule } from '@angular/material/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import { SidenavAutosize } from './shared/sidenav-autosize/sidenav-autosize.component';
import { SalesManagementComponent } from './sales-management/sales-management.component';
import { PurchaseManagementComponent } from './purchase-management/purchase-management.component';
import { AnalystDashboardComponent } from './analyst-dashboard/analyst-dashboard.component';
import { AnalystSidenavComponent } from './shared/analyst-sidenav/analyst-sidenav.component';
import { AnalystSalesComponent } from './analyst-sales/analyst-sales.component';
import { AnalystPurchaseComponent } from './analyst-purchase/analyst-purchase.component';
import { UploadService } from './shared/services/upload.service';
import { CookieService } from 'ngx-cookie-service';
import { RoleGuard } from './guards/role.guard';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { DownloadService } from './shared/services/download.service';
import { UserService } from './shared/services/user.service';
import { DataGridComponent } from './shared/data-grid/data-grid.component';
import { EditUserModalComponent } from './edit-user-modal/edit-user-modal.component';
//import { PurchaseManagementComponent } from './purchase-management/purchase-management.component';


const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Admin' } // Only accessible by Admin role
  },
  { path: 'user-management', 
    component: UserManagementComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Admin' } 
  }, // Add this route
  { path: 'sales', 
    component: SalesManagementComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Admin' } 
  }, // Add this route
  { path: 'purchases', 
    component: PurchaseManagementComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Admin' } 
  },
  { path: 'analyst-dashboard', 
    component: AnalystDashboardComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Analyst' } 
   }, // Add this route
  { path: 'analyst/sales', 
    component: AnalystSalesComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Analyst' } 
   }, // Add this route
  { path: 'analyst/purchase', 
    component: AnalystPurchaseComponent,
    canActivate: [RoleGuard],
    data: { role: 'ROLE_Analyst' } 
   }, 
   { path: 'unauthorized', component: LoginComponent } // Unauthorized page
]

@NgModule({
  declarations: [
    // Declare your components here
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    AdminDashboardComponent,
    UserManagementComponent,
    LeftNavComponent,
    SidenavAutosize,
    SalesManagementComponent,
    PurchaseManagementComponent,
    AnalystDashboardComponent,
    AnalystSidenavComponent,
    AnalystSalesComponent,
    AnalystPurchaseComponent,
    DataGridComponent,
    EditUserModalComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes) ,
    NgChartsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    CdkTableModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    
  ],
  providers: [
    AuthService,
    UploadService,
    CookieService,
    DownloadService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true // Allow multiple interceptors
    },
    provideHttpClient(withInterceptorsFromDi()) // Use the new provideHttpClient function with interceptors
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }