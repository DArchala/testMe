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
    this.examModel.questions = [new SingleChoiceQuestion("",
      [new Answer(), new Answer()], "single", "")];
    this.examModel.id = 0;
    this.examModel.examName = "examName";
    this.examModel.timeInSeconds = 1800;
    this.examModel.difficultyLevel = "MEDIUM";
  }

  examModel!: Exam;
  newQuestionType = "single";

  addNewQuestion() {

    let quest: any;
    switch (this.newQuestionType) {
      case "single":
        quest = new SingleChoiceQuestion("", [new Answer(), new Answer()], "single", "");
        break;
      case "multiple":
        quest = new MultipleChoiceQuestion("", [new Answer(), new Answer()], "multiple", "");
        break;
      case "short":
        quest = new ShortAnswerQuestion("", [new Answer(), new Answer()], "short", "");
        break;
    }
    this.examModel.questions.push(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    question.answers.push(new Answer());
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
}
