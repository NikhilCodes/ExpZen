import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AuthService } from '../../core/service/auth.service';
import { SettingsService } from '../../core/service/settings.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css', '../home/home.component.css'],
})
export class SettingsComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private settingsService: SettingsService,
    private snackBar: MatSnackBar,
  ) {}

  name = new FormControl(this.authService.user.name);
  email = new FormControl(this.authService.user.email);

  existingName = new FormControl(this.authService.user.name);

  ngOnInit(): void {

  }

  updateName(): void {
    this.settingsService.updateName(this.name.value).subscribe(_ => {
      this.existingName.setValue(this.name.value);
      this.snackBar.open('Name Updated!', '', { duration: 2000 });
    });
  }

  onLogout(): void {
    this.authService.logout();
  }
}
