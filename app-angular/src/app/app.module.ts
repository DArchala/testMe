import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import {AppRoutingModule} from "./app-routing.module";
import { ExamsComponent } from './pages/exams/exams.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import {HttpClientModule} from "@angular/common/http";
import { ExamComponent } from './pages/exam/exam.component';
import { ExamTimerPipe } from './pipes/exam-timer.pipe';
import {FormsModule} from "@angular/forms";
import { MultipleChoiceQuestionComponent } from './pages/question-types/multiple-choice-question/multiple-choice-question.component';
import { ShortAnswerQuestionComponent } from './pages/question-types/short-answer-question/short-answer-question.component';
import { SingleChoiceQuestionComponent } from './pages/question-types/single-choice-question/single-choice-question.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ExamsComponent,
    PageNotFoundComponent,
    ExamComponent,
    ExamTimerPipe,
    MultipleChoiceQuestionComponent,
    ShortAnswerQuestionComponent,
    SingleChoiceQuestionComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
