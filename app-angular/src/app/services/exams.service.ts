import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Exam} from "../models/exam";
import {tap} from "rxjs";
import {Answer} from "../models/answer";
import {SingleChoiceQuestion} from "../models/questionTypes/single-choice-question";
import {MultipleChoiceQuestion} from "../models/questionTypes/multiple-choice-question";
import {ShortAnswerQuestion} from "../models/questionTypes/short-answer-question";

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

  validateExamTimer(examHours: number, examMinutes: number) {
    if (examHours === null || examHours === undefined) examHours = 0;
    if (examMinutes === null || examMinutes === undefined) examMinutes = 0;
    if (examMinutes === 0 && examMinutes === 0) {
      alert("Czas egzaminu nie może być zerowy.");
      return false;
    }
    if (examHours > 23 || examHours < 0) {
      alert("Czas egzaminu (godziny) nie może być ujemny ani większy od 23.");
      return false;
    }
    if (examMinutes > 59 || examMinutes < 0) {
      alert("Czas egzaminu (minuty) nie może być ujemny ani większy od 59.");
      return false;
    }
    return true;
  }

  validateExamQuestions(examModel: Exam) {
    if (examModel.questions.length < 1) {
      alert("Egzamin musi zawierać conajmniej jedno pytanie.");
      return false;
    }

    for (let question of examModel.questions) {

      if (question.content.length === 0) {
        alert("Każde pytanie musi zawierać treść.");
        return false;
      }

      if (question.answers.length === 0) {
        alert("Każde pytanie musi zawierać conajmniej jedną odpowiedź.");
        return false;
      }

      if ((question.type === 'single' || question.type === 'multiple') && question.answers.length === 1) {
        alert("Każde pytanie jednokrotnego lub wielokrotnego wyboru musi zawierać conajmniej dwie odpowiedzi.");
        return false;
      }

      for (let answer of question.answers) {

        if (answer.content.length === 0) {
          alert("Każda odpowiedź musi zawierać treść.");
          return false;
        }

        let amountOfCorrectAnswers = 0;
        question.answers.forEach(answer => {
          if (answer.correctness) amountOfCorrectAnswers++;
        });
        if (amountOfCorrectAnswers === 0) {
          alert("Każde pytanie musi zawierać conajmniej jedną prawidłową odpowiedź.");
          return false;
        }

      }
    }
    return true;
  }

  validateExamName(examModel: Exam) {
    if (examModel.examName.length === 0) {
      alert("Nazwa egzaminu nie może być pusta.");
      return false;
    } else if (examModel.examName.length > 60) {
      alert("Nazwa egzaminu nie może być dłuższa niż 60 znaków.");
      return false;
    }
    return true;
  }

  validateDifficultyLevel(examModel: Exam) {
    if (examModel.difficultyLevel.length === 0) {
      alert("Poziom trudności egzaminu nie może być pusty.");
      return false;
    } else if (examModel.difficultyLevel.length > 60) {
      alert("Poziom trudności egzaminu nie może być dłuższy niż 60 znaków.");
      return false;
    }
    return true;
  }

  validateExamTime(examModel: Exam) {
    if (examModel.timeInSeconds < 60 || examModel.timeInSeconds > 86400) {
      alert("Czas egzaminu musi wynosić od 60 do 86400 sekund.");
      return false;
    }
    return true;
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
