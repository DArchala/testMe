import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ExamAttempt} from "../models/exam-attempt";

@Injectable({
  providedIn: 'root'
})
export class ExamAttemptsService {

  constructor(private httpClient: HttpClient) { }

  private url = "http://localhost:8080/api";

  getMyExamAttempts() {
    return this.httpClient.get<ExamAttempt[]>(this.url + `/examAttempts/getMyExamAttempts`);
  }
}
