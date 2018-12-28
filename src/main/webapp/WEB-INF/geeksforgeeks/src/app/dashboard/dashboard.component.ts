import {OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {Component} from '@angular/core';
import {MatCheckboxChange, MatSnackBar, MatTableDataSource} from '@angular/material';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {isSuccess} from '@angular/http/src/http_utils';


export interface PeriodicElement {
  title: string;
  difficultyLevel: String;
  questionRating: String;
  companyTags;
  url;
  topicTags;
}

let headers = new HttpHeaders({
  'Access-Control-Allow-Headers': 'Content-Type',
  'Access-Control-Allow-Methods': 'GET',
  'Access-Control-Allow-Origin': '*',
  'Content-Type': 'application/json'
});


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  private queryEndpoint = 'http://localhost:8081/api/questions';
  public data;
  public tableDataArray;
  public dataSource;
  public selection = new SelectionModel<PeriodicElement>(true, []);

  constructor(public http: HttpClient, public snackBar: MatSnackBar) {
    let scopeObject = this;
    this.http.get(this.queryEndpoint).subscribe(
      data => {
        scopeObject.tableDataArray = data;
        scopeObject.dataSource = new MatTableDataSource<PeriodicElement>(scopeObject.tableDataArray);

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

  ngOnInit() {
    console.log(this.tableDataArray);
  }

  displayedColumns: string[] = ['select', 'Title', 'Difficulty', 'Rating', 'Topic Tags', 'Company Tags'];


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
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
