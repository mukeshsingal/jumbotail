import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-notes-dialog',
  templateUrl: './notes-dialog.component.html',
  styleUrls: ['./notes-dialog.component.css']
})
export class NotesDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<NotesDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  save(content: string) {
    this.dialogRef.close({'content': content, 'trigger': 'saved'});
  }

  canceled(content: string) {
    this.dialogRef.close({'content': content, 'trigger': 'canceled'});
  }
}
