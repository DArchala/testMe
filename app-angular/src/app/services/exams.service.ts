import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Exam} from "../models/exam";
import {tap} from "rxjs";
import {Answer} from "../models/answer";
import {SingleChoiceQuestion} from "../models/questionTypes/single-choice-question";
import {MultipleChoiceQuestion} from "../models/questionTypes/multiple-choice-question";
import {ShortAnswerQuestion} from "../models/questionTypes/short-answer-question";
import {ExamForm} from "../models/exam-form";

@Injectable({
  providedIn: 'root'
})
export class ExamsService {

  private url = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) {
  }

  getExams() {
    return this.httpClient.get<Exam[]>(this.url + `/exams`);
  }

  getExamById(examId: string | any) {
    return this.httpClient.get<Exam>(this.url + `/exams/exam/` + examId).pipe(tap(console.log));
  }

  postExamToCheckCorrectness(examForm: ExamForm) {
    return this.httpClient.post<Exam>(this.url + `/exams/exam`, examForm).pipe(tap(console.log));
  }

  postExamGetMaxPoints(examId: string | any) {
    return this.httpClient.post<number>(this.url + `/exams/exam/` + examId, null).pipe(tap(console.log));
  }

  getNewExamData() {
    return this.httpClient.get<any[]>(this.url + `/new-exam`).pipe(tap(console.log));
  }

  postNewExam(exam: Exam) {
    return this.httpClient.post<Exam>(this.url + `/new-exam/save`, exam).pipe(tap(console.log));
  }

  getExamToEditById(examId: string | any) {
    return this.httpClient.get<Exam>(this.url + `/exams/edit/` + examId).pipe(tap(console.log));
  }

  putExam(exam: Exam) {
    return this.httpClient.put<Exam>(this.url + `/exams/edit`, exam).pipe(tap(console.log));
  }

  deleteExam(examId: any) {
    return this.httpClient.delete<any>(this.url + `/exams/delete/` + examId).pipe(tap(console.log));
  }

  getNewQuestion(questionType: string) {
    let quest: any;
    let answer = new Answer(null, "", false);
    let answer2 = new Answer(null, "", false);

    switch (questionType) {
      case "single":
        quest = new SingleChoiceQuestion(null, "", [answer, answer2], "single", "");
        break;
      case "multiple":
        quest = new MultipleChoiceQuestion(null, "", [answer, answer2], "multiple", "");
        break;
      case "short":
        answer.correctness = true;
        quest = new ShortAnswerQuestion(null, "", [answer], "short", "");
        break;
    }
    return quest;
  }

  countCorrectAnswers(exam: Exam) {
    let counter = 0;
    exam.questions?.forEach(question => {
      question.answers?.forEach(answer => {
        if (answer.correctness) counter++;
      })
    });
    return counter;
  }

}
