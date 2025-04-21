import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartData, ChartType } from 'chart.js';
import { AdminService } from '../shared/services/admin.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  standalone: false,
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  isPanelCollapsed = false; // State for toggling the panel
  dashboardData: any;
  totalUploads: number = 0;
  userActivityDataSource = new MatTableDataSource<any>();
  displayedColumns: string[] = ['username', 'action', 'timestamp'];

  // Chart data for System Metrics
  cpuUsageChartData: ChartData<'doughnut'> = {
    labels: ['Used CPU', 'Free CPU'],
    datasets: [
      {
        data: [45, 55],
        backgroundColor: ['#007bff', '#FFC107']
      }
    ]
  };


  memoryUsageChartData: ChartData<'doughnut'> = {
    labels: ['Used Memory', 'Free Memory'],
    datasets: [
      {
        data: [68, 32],
        backgroundColor: ['#9C27B0', '#FF9800']
      }
    ]
  };

  barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      {
        label: 'System Statistics',
        data: [],
        backgroundColor: []
      }
    ]
  };

  pieChartData: ChartData<'pie'> = {
    labels: [],
    datasets: [
      {
        label: 'File Processing Statistics',
        data: [],
        backgroundColor: []
      }
    ]
  };

 // Chart Options
 chartOptions = {
  responsive: true,
   plugins: {
    scales: {
      x: {
        beginAtZero: true
      },
      y: {
        beginAtZero: true
      }
    },
    legend: {
      display: true,
      position: 'top'
    }
  }
};

constructor(private adminService: AdminService) {}
 
ngOnInit(): void { 
  this.fetchDashboardData(); // Fetch dashboard data on component initialization
  } 
 togglePanel(): void {
    this.isPanelCollapsed = !this.isPanelCollapsed;
  }

  fetchDashboardData(): void {
    this.adminService.getDashboardStats().subscribe(
      (data) => {
        this.dashboardData = data;
        this.userActivityDataSource.data = this.dashboardData.userActivities; // Populate the MatTableDataSource with user activity data
        this.pieChartData = {
          labels: ['Successful', 'Failed'],
          datasets: [
            {
              label: 'File Processing Statistics',
              data: [this.dashboardData.successfulProcesses, this.dashboardData.failedProcesses],
              backgroundColor: ['#28a745', '#E57373']
            }
          ]
        };
        this.barChartData = {
          labels: ['Total Users', 'Total Uploads'],
          datasets: [
            {
              label: 'System Statistics',
              data: [this.dashboardData.totalUsers, this.dashboardData.totalUploads],
              backgroundColor: ['#28a745', '#dc3545']
            }
          ]
        };
        
    
    },
      (error) => {
        console.error('Error fetching dashboard data:', error);
      }
    );
  }
}