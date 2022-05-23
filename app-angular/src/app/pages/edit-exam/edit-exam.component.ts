import {Component} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {DialogService} from "../../services/dialog.service";
import {Answer} from "../../models/answer";
import {SingleChoiceQuestion} from "../../models/questionTypes/single-choice-question";
import {Exam} from "../../models/exam";
import {MultipleChoiceQuestion} from "../../models/questionTypes/multiple-choice-question";
import {ShortAnswerQuestion} from "../../models/questionTypes/short-answer-question";
import {Question} from "../../models/question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-edit-exam',
  templateUrl: './edit-exam.component.html',
  styleUrls: ['./edit-exam.component.css']
})
export class EditExamComponent {

  constructor(private examService: ExamsService, private dialogService: DialogService,
              private route: ActivatedRoute) {

    this.examId = this.route.snapshot.paramMap.get('id');
    this.examService.getNewExamData().subscribe(data => this.difficultyLevels = data);
    this.examService.getExamToEditById(this.examId).subscribe(data => {
      this.examModel = data;
      this.examModel.questions = data.questions;
      this.examHours = data.timeInSeconds/3600;
      this.examMinutes = (data.timeInSeconds%3600)/60;
    });
  }

  examModel = new Exam();
  examId: any;
  newQuestionType = "single";
  difficultyLevels: any;
  examHours!: number;
  examMinutes!: number;

  addNewQuestion() {
    let quest: any;
    let answer = new Answer(0, "", false);
    let answer2 = new Answer(0, "", false);

    switch (this.newQuestionType) {
      case "single":
        quest = new SingleChoiceQuestion(0, "", [answer, answer2], "single", "");
        break;
      case "multiple":
        quest = new MultipleChoiceQuestion(0, "", [answer, answer2], "multiple", "");
        break;
      case "short":
        answer.correctness = true;
        quest = new ShortAnswerQuestion(0, "", [answer], "short", "");
        break;
    }
    this.examModel.questions.push(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    let answer = new Answer(0, "", false);
    if (question.type === 'short') {
      answer.correctness = true;
    }
    question.answers.push(answer);
  }

  deleteThisQuestion(question: Question) {
    let index = this.examModel.questions.indexOf(question, 0);
    if (index > -1) {
      this.examModel.questions.splice(index, 1);
    }
  }

  deleteThisAnswer(question: Question, answer: Answer) {
    let index = question.answers.indexOf(answer, 0);
    if (index > -1) {
      question.answers.splice(index, 1);
    }
  }

  uncheckOtherAnswers(answer: Answer, question: Question) {
    if (question.type === 'single') {
      question.answers.forEach(answer => answer.correctness = false);
      answer.correctness = true;
    }
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

  postExam() {
    this.examModel.timeInSeconds = this.getExamTime();
    if (this.validateExamName() &&
      this.validateDifficultyLevel() &&
      this.validateExamTimer() &&
      this.validateExamQuestions() &&
      this.validateExamTime()) {
      this.examService.putExam(this.examModel).subscribe(console.log);
      alert("Egzamin został nadpisany!");
    } else alert("Nadpisywanie egzaminu nie powiodło się.");

  }

  private validateExamName() {
    if (this.examModel.examName.length === 0) {
      alert("Nazwa egzaminu nie może być pusta.");
      return false;
    } else if (this.examModel.examName.length > 60) {
      alert("Nazwa egzaminu nie może być dłuższa niż 60 znaków.");
      return false;
    }
    return true;
  }

  private validateDifficultyLevel() {
    if (this.examModel.difficultyLevel.length === 0) {
      alert("Poziom trudności egzaminu nie może być pusty.");
      return false;
    } else if (this.examModel.difficultyLevel.length > 60) {
      alert("Poziom trudności egzaminu nie może być dłuższy niż 60 znaków.");
      return false;
    }
    return true;
  }

  private validateExamTime() {
    if (this.examModel.timeInSeconds < 60 || this.examModel.timeInSeconds > 86400) {
      alert("Czas egzaminu musi wynosić od 60 do 86400 sekund.");
      return false;
    }
    return true;
  }

  private validateExamQuestions() {
    if (this.examModel.questions.length < 1) {
      alert("Egzamin musi zawierać conajmniej jedno pytanie.");
      return false;
    }

    for (let question of this.examModel.questions) {

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

  private validateExamTimer() {
    if (this.examHours === null || this.examHours === undefined) this.examHours = 0;
    if (this.examMinutes === null || this.examMinutes === undefined) this.examMinutes = 0;
    if (this.examHours === 0 && this.examMinutes === 0) {
      alert("Czas egzaminu nie może być zerowy.");
      return false;
    }
    if (this.examHours > 23 || this.examHours < 0) {
      alert("Czas egzaminu (godziny) nie może być ujemny ani większy od 23.");
      return false;
    }
    if (this.examMinutes > 59 || this.examMinutes < 0) {
      alert("Czas egzaminu (minuty) nie może być ujemny ani większy od 59.");
      return false;
    }
    return true;
  }

  private getExamTime() {
    return (this.examHours * 3600) + (this.examMinutes * 60);
  }

  saveExam(information: string) {
    const answer = this.dialogService.getDialog(information);

    answer.afterClosed().subscribe(accept => {
      if (accept) this.postExam();
    });

  }

}
