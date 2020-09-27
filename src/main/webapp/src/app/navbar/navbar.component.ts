import {Component, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Output() navToggle = new EventEmitter<boolean>();
  
  navOpen() {
    this.navToggle.emit(true);
  }


  title = 'app';
  options: FormGroup;

  constructor(fb: FormBuilder) {
    this.options = fb.group({
      'fixed': false,
      'top': 0,
      'bottom': 0,
    });
  }

  shouldRun = true

  ngOnInit() {
  }

}