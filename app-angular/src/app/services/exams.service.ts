import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Exam} from "../models/exam";

@Injectable({
  providedIn: 'root'
})
export class ExamsService {

  constructor(private httpClient: HttpClient) { }

  getExams() {
    return this.httpClient.get<Exam[]>(`http://localhost:8080/exams`);
  }

  getExamById(examId: string | any) {
    return this.httpClient.get<Exam>(`http://localhost:8080/exams/exam/` + examId);
  }

}
