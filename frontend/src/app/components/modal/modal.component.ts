import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent implements OnInit {
  @Input()
  visibility: boolean;
  @Output()
  visibilityChange = new EventEmitter<boolean>();

  @Input()
  title: string;

  onModalCloseModified(): void {
    this.visibility = false;
    this.visibilityChange.emit(this.visibility);
  }

  constructor() { }

  ngOnInit(): void { }
}
