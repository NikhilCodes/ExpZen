import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent implements OnInit {

  dashboardPageUrl = '/';
  analyticsPageUrl = '/analytics';
  userSettingsPageUrl = '/settings';

  constructor(private router: Router) {}

  ngOnInit(): void {
  }

  navigateToDashboard(): void {
    this.router.navigate([this.dashboardPageUrl]);
  }

  navigateToAnalytics(): void {
    this.router.navigate([this.analyticsPageUrl]);
  }

  navigateToUserSettings(): void {
    this.router.navigate([this.userSettingsPageUrl]);
  }
}
