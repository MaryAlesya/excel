import { Component } from '@angular/core';
import { ChartOptions, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-analyst-dashboard',
  templateUrl: './analyst-dashboard.component.html',
  styleUrls: ['./analyst-dashboard.component.scss'],
  standalone: false
})
export class AnalystDashboardComponent {
  // Metrics
  totalUploads = 120;
  lastUploadTime = '2025-04-18 10:30 AM';

  // Chart Data for Download History
  downloadHistoryLabels = ['January', 'February', 'March', 'April', 'May', 'June'];
  downloadHistoryData = [10, 20, 15, 30, 25, 40];
  downloadHistoryChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      }
    },
    scales: {
      x: {
        beginAtZero: true
      },
      y: {
        beginAtZero: true
      }
    }
  };
 downloadHistoryChartData: ChartData<'bar'> = {
      labels: this.downloadHistoryLabels,
      datasets: [
        {
          label: 'Downloads',
          data: this.downloadHistoryData,
          backgroundColor: 'rgba(65, 138, 194, 0.93)',
        }
      ]
    };
  
}