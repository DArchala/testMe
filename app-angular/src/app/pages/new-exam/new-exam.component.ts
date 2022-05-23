import {Component} from '@angular/core';
import {Exam} from "../../models/exam";
import {Answer} from "../../models/answer";
import {Question} from "../../models/question";
import {SingleChoiceQuestion} from "../../models/questionTypes/single-choice-question";
import {ExamsService} from "../../services/exams.service";
import {DialogService} from "../../services/dialog.service";

@Component({
  selector: 'app-new-exam',
  templateUrl: './new-exam.component.html',
  styleUrls: ['./new-exam.component.css']
})
export class NewExamComponent {

  constructor(public examService: ExamsService, private dialogService: DialogService) {
    this.examService.getNewExamData().subscribe(data => this.difficultyLevels = data);
    let sampleAnswer1 = new Answer(0, "", false);
    let sampleAnswer2 = new Answer(0, "", false);
    this.examModel.questions = [new SingleChoiceQuestion(0, "",
      [sampleAnswer1, sampleAnswer2], "single", "")];
    this.examModel.id = 0;
    this.examModel.examName = "";
    this.examModel.timeInSeconds = 0;
    this.examModel.difficultyLevel = "MEDIUM";
  }

  examModel = new Exam();
  newQuestionType = "single";
  difficultyLevels: any;
  examHours = 0;
  examMinutes = 0;

  addNewQuestion(questionType: string) {
    let quest = this.examService.getNewQuestion(questionType);
    this.examModel.questions.push(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    let answer = new Answer(null, "", false);
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

  postExam() {
    this.examModel.timeInSeconds = this.getExamTime();
    if (this.examService.validateExamName(this.examModel) &&
      this.examService.validateDifficultyLevel(this.examModel) &&
      this.examService.validateExamTimer(this.examHours, this.examMinutes) &&
      this.examService.validateExamQuestions(this.examModel) &&
      this.examService.validateExamTime(this.examModel)) {
      this.examService.postNewExam(this.examModel).subscribe(console.log);
      alert("Egzamin został dodany!");
      window.location.reload();
    } else alert("Dodawanie egzaminu nie powiodło się.");

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
