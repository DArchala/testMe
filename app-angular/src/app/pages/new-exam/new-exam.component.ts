import {Component} from '@angular/core';
import {Exam} from "../../models/exam";
import {ShortAnswerQuestion} from "../../models/questionTypes/short-answer-question";
import {Answer} from "../../models/answer";
import {Question} from "../../models/question";
import {SingleChoiceQuestion} from "../../models/questionTypes/single-choice-question";
import {MultipleChoiceQuestion} from "../../models/questionTypes/multiple-choice-question";

@Component({
  selector: 'app-new-exam',
  templateUrl: './new-exam.component.html',
  styleUrls: ['./new-exam.component.css']
})
export class NewExamComponent {

  constructor() {
    this.examModel = new Exam();
    let sampleAnswer1 = new Answer("", false);
    let sampleAnswer2 = new Answer("", false);
    this.examModel.questions = [new SingleChoiceQuestion("",
      [sampleAnswer1, sampleAnswer2], "single", "")];
    this.examModel.id = 0;
    this.examModel.examName = "examName";
    this.examModel.timeInSeconds = 1800;
    this.examModel.difficultyLevel = "MEDIUM";
  }

  examModel!: Exam;
  newQuestionType = "single";

  addNewQuestion() {
    let quest: any;
    let answer = new Answer("", false);
    let answer2 = new Answer("", false);

    switch (this.newQuestionType) {
      case "single":
        quest = new SingleChoiceQuestion("", [answer, answer2], "single", "");
        break;
      case "multiple":
        quest = new MultipleChoiceQuestion("", [answer, answer2], "multiple", "");
        break;
      case "short":
        answer.correctness = true;
        quest = new ShortAnswerQuestion("", [answer], "short", "");
        break;
    }
    this.examModel.questions.push(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    let answer = new Answer("", false);
    if (question.type === 'short') {
      answer.correctness = true;
    }
    question.answers.push(answer);
  }

  displayExam() {
    console.log(this.examModel.toString());
    this.examModel.questions.forEach(question => console.log(question));
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
    exam.questions.forEach(question => {
      question.answers.forEach(answer => {
        if (answer.correctness) counter++;
      })
    });
    return counter;
  }

  postExam() {
    this.validateExamName();
    this.validateDifficultyLevel();
    this.validateExamTime();
    this.validateExamQuestions();
  }

  private validateExamName() {
    if (this.examModel.examName.length === 0) alert("Nazwa egzaminu nie może być pusta.");
    else if (this.examModel.examName.length > 60) alert("Nazwa egzaminu nie może być dłuższa niż 60 znaków.");
  }

  private validateDifficultyLevel() {
    if (this.examModel.difficultyLevel.length === 0) alert("Poziom trudności egzaminu nie może być pusty.");
    else if (this.examModel.difficultyLevel.length > 60) alert("Poziom trudności egzaminu nie może być dłuższy niż 60 znaków.");
  }

  private validateExamTime() {
    if (this.examModel.timeInSeconds < 60 || this.examModel.timeInSeconds > 86400) alert("Czas egzaminu musi wynosić od 60 do 86400 sekund.");
  }

  private validateExamQuestions() {
    let breakAll = false;

    if (this.examModel.questions.length < 1) alert("Egzamin musi zawierać conajmniej jedno pytanie.");

    for (let question of this.examModel.questions) {

      if (question.content.length === 0) {
        alert("Każde pytanie musi zawierać treść.");
        break;
      }

      if (question.answers.length === 0) {
        alert("Każde pytanie musi zawierać conajmniej jedną odpowiedź.");
        break;
      }

      if ((question.type === 'single' || question.type === 'multiple') && question.answers.length === 1) {
        alert("Każde pytanie jednokrotnego lub wielokrotnego wyboru musi zawierać conajmniej dwie odpowiedzi.");
        break;
      }

      for (let answer of question.answers) {

        if (answer.content.length === 0) {
          alert("Każda odpowiedź musi zawierać treść.");
          breakAll = true;
          break;
        }

        let amountOfCorrectAnswers = 0;
        question.answers.forEach(answer => {
          if (answer.correctness) amountOfCorrectAnswers++;
        });
        if (amountOfCorrectAnswers === 0) {
          alert("Każde pytanie musi zawierać conajmniej jedną prawidłową odpowiedź.");
          breakAll = true;
          break;
        }

      }

      if (breakAll) break;
    }
  }
}
