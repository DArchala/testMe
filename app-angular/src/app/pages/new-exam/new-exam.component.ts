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
    this.examModel.questions = [new ShortAnswerQuestion("Wpisz treść pierwszego pytania",
      [new Answer(), new Answer()], "short", "")];
  }

  examModel!: Exam;
  newQuestionType = "single";

  addNewQuestion() {
    console.log(this.examModel.toString());
    console.log(this.newQuestionType);
    let quest: any;
    switch (this.newQuestionType) {
      case "single":
        quest = new SingleChoiceQuestion("single", [new Answer(), new Answer()], "", "");
        break;
      case "multiple":
        quest = new MultipleChoiceQuestion("multiple", [new Answer(), new Answer()], "", "");
        break;
      case "short":
        quest = new ShortAnswerQuestion("short", [new Answer(), new Answer()], "", "");
        break;
    }
    this.examModel.questions.push(quest);
    console.log(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    question.answers.push(new Answer());
  }
}
