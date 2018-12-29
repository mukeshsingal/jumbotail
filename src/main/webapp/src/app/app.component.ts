import {Inject, OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {Component} from '@angular/core';
import {MAT_DIALOG_DATA, MatCheckboxChange, MatDialog, MatDialogRef, MatSnackBar, MatTableDataSource} from '@angular/material';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {NotesDialogComponent} from './notes-dialog/notes-dialog.component';


export interface PeriodicElement {
  title: string;
  difficultyLevel: String;
  questionRating: String;
  companyTags: CompanyTag[];
  url;
  topicTags: TopicTag[];
}


export interface CompanyTag {
  id: string;
  name: String;
}

export interface Notes {
  id: string;
  notes: string;
  questionId: string;
}

export interface TopicTag {
  id: string;
  name: String;
}

let headers = new HttpHeaders({
  'Access-Control-Allow-Headers': 'Content-Type',
  'Access-Control-Allow-Methods': 'GET',
  'Access-Control-Allow-Origin': '*',
  'Content-Type': 'application/json'
});


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  private queryEndpoint = 'http://localhost:8081/api/questions';
  public data;
  public tableDataArray;
  public dataSource;
  public selection = new SelectionModel<PeriodicElement>(true, []);

  constructor(public http: HttpClient, public snackBar: MatSnackBar, public dialog: MatDialog) {
    let scopeObject = this;
    this.http.get(this.queryEndpoint).subscribe(
      data => {
        scopeObject.tableDataArray = data;
        scopeObject.dataSource = new MatTableDataSource<PeriodicElement>(scopeObject.tableDataArray);
        scopeObject.dataSource.filterPredicate =
          (data: PeriodicElement, filter: string) => {
            if (data.title.toLowerCase().indexOf(filter.toLowerCase()) >= 0) return true;
            if (data.difficultyLevel.toLowerCase().indexOf(filter.toLowerCase()) >= 0) return true;

            console.log(data.difficultyLevel);
            console.log(filter);

            if (data.questionRating.toLowerCase().indexOf(filter.toLowerCase()) >= 0) return true;
            if (data.companyTags.find(value => value.name.toLowerCase().startsWith(filter)) != undefined) return true;
            if (data.topicTags.find(value => value.name == filter) != undefined) return true;
          };

        var newArray = scopeObject.tableDataArray.filter(function (objects) {
          return objects.status == 'DONE';
        });

        scopeObject.selection = new SelectionModel<PeriodicElement>(true, newArray);

        console.log(scopeObject.tableDataArray);
        console.log(scopeObject.selection);
      },
      err => console.error(err),
      () => console.log('Printed all the data from database')
    );
    console.log(this.tableDataArray);
  }

  openDialog(row): void {
    let notesContent = "";
    this.http.get<Notes>('http://localhost:8081/api/note/' + row.id).subscribe(
      data => {
        let notes = "";
        let header;
        if(data != undefined)  {
          notes = data.notes;
        }
        const dialogRef = this.dialog.open(NotesDialogComponent, {
          width: '1000px',
          data : {
            "myrow" : row,
            'notes' : notes
          }
        });

        dialogRef.afterClosed().subscribe(result => {
          if(result == undefined)  {
            console.log('The dialog was closed' + result);
          }
          else {
            let x;
            if(data != undefined) {
              x = {'id': data.id, 'questionId': row.id, 'notes': result};
            }
            else {
              x = {'questionId': row.id, 'notes': result};
            }
            this.http.post('http://localhost:8081/api/notes/', x, {headers: headers}).subscribe(
              data => {
                let message = "Notes updated"
                this.snackBar.open(message, '', {
                  duration: 6000,
                  verticalPosition: 'bottom',
                  horizontalPosition: 'end'
                });
              },
              err => console.error(err),
              () => console.log('')
            );
          }
        });
      },
      err => console.error(err),
      () => console.log('Printed all the data from database')
    );


  }

  ngOnInit() {
    console.log(this.tableDataArray);
  }

  displayedColumns: string[] = ['select', 'SN', 'Title', 'Difficulty', 'Rating', 'Topic Tags', 'Company Tags', 'Notes'];


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  getCheckBoxEvent(event: MatCheckboxChange, selection: SelectionModel<PeriodicElement>, row) {
    console.log(event);
    console.log(selection);
    console.log(row);

    let newStatus = event.checked ? 'DONE' : 'NOT_DONE';
    this.http.post('http://localhost:8081/api/question/' + row.id + '/status', {'status': newStatus}, {headers: headers}).subscribe(
      data => {
        let message = event.checked ?
          'Question ' + row.title + ' marked as done.' :
          'Question ' + row.title + ' marked as not done.';
        this.snackBar.open(message, '', {
          duration: 6000,
          verticalPosition: 'bottom',
          horizontalPosition: 'end'
        });
        return event ? selection.toggle(row) : null;
      },
      err => console.error(err),
      () => console.log('done loading foods')
    );
  }

  applyFilter(filterValue: string) {
    console.log('Called filter ' + filterValue);
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}


