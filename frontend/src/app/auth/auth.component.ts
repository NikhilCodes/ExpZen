import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ height: 0, opacity: 0 }),
            animate('0.3s ease-out',
              style({ height: 76, opacity: 1 }))
          ]
        ),
        transition(
          ':leave',
          [
            style({ height: 76, opacity: 1 }),
            animate('0.3s ease-in',
              style({ height: 0, opacity: 0 }))
          ]
        )
      ],
    ),
  ],
})
export class AuthComponent implements OnInit {
  name = new FormControl('');
  email = new FormControl('');
  password = new FormControl('');
  showPassword = false;

  authType: 'REGISTER' | 'LOGIN' = 'LOGIN';

  constructor() { }

  ngOnInit(): void {
  }

}
