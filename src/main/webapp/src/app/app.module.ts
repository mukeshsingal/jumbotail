import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { AdminComponent } from "./admin/admin/admin.component";
import { RouterModule } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AngularFontAwesomeModule} from "angular-font-awesome";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatSidenavModule,
  MatSelectModule
} from "@angular/material";
import { NotesDialogComponent } from "./notes-dialog/notes-dialog.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { QuestionlistComponent } from "./questionlist/questionlist.component";
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    NotesDialogComponent,
    NavbarComponent,
    QuestionlistComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatCheckboxModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    FormsModule,
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatSelectModule,
    AngularFontAwesomeModule,

    RouterModule.forRoot([
      { path: "", component: AppComponent },
      { path: "home", component: HomeComponent, outlet : "left"},
      { path: "admin", component: AdminComponent},
      { path: "questions", component: QuestionlistComponent, outlet : "left" }
    ])
  ],
  entryComponents: [NotesDialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
