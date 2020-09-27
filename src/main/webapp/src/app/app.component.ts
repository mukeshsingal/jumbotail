import {Inject, OnInit} from '@angular/core';
import {Component} from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  title = 'app';
  options: FormGroup;
  
  shouldRun = true

  constructor(fb: FormBuilder) {

    this.options = fb.group({
      'fixed': false,
      'top': 0,
      'bottom': 0,
    });
  }
  ngOnInit() {
    //console.log(this.tableDataArray);
  }
}


