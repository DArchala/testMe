import {CUSTOM_ELEMENTS_SCHEMA, forwardRef, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {AppRoutingModule} from "./app-routing.module";
import {ExamsComponent} from './pages/exams/exams.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {HttpClientModule} from "@angular/common/http";
import {ExamComponent} from './pages/exam/exam.component';
import {ExamTimerPipe} from './pipes/exam-timer.pipe';
import {FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule} from "@angular/forms";
import {NewExamComponent} from './pages/new-exam/new-exam.component';
import {MatDialogModule} from '@angular/material/dialog';
import {DialogComponent} from './pages/dialog/dialog.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {EditExamComponent} from './pages/edit-exam/edit-exam.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatRadioModule} from "@angular/material/radio";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ExamsComponent,
    PageNotFoundComponent,
    ExamComponent,
    ExamTimerPipe,
    NewExamComponent,
    DialogComponent,
    EditExamComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ],
  entryComponents: [DialogComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTooltipModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
