import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Exam} from "../models/exam";
import {tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ExamsService {

  private url = "http://localhost:8080";

  constructor(private httpClient: HttpClient) {
  }

  getExams() {
    return this.httpClient.get<Exam[]>(this.url + `/exams`);
  }

  getExamById(examId: string | any) {
    return this.httpClient.get<Exam>(this.url + `/exams/exam/` + examId).pipe(tap(console.log));
  }

  postExamToCheckCorrectness(exam: Exam) {
    return this.httpClient.post<Exam>(this.url + `/exams/exam`, exam).pipe(tap(console.log));
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
    return this.httpClient.delete<any>(this.url + `/exams/delete/` + examId).subscribe(console.log);
  }

}
