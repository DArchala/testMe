import {Component} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {DialogService} from "../../services/dialog.service";
import {Answer} from "../../models/answer";
import {Exam} from "../../models/exam";
import {Question} from "../../models/question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-edit-exam',
  templateUrl: './edit-exam.component.html',
  styleUrls: ['./edit-exam.component.css']
})
export class EditExamComponent {

  constructor(public examService: ExamsService, private dialogService: DialogService,
              private route: ActivatedRoute) {

    this.examId = this.route.snapshot.paramMap.get('id');
    this.examService.getNewExamData().subscribe(data => this.difficultyLevels = data);
    this.examService.getExamToEditById(this.examId).subscribe(data => {
      this.examModel = data;
      this.examModel.questions = data.questions;
      this.examHours = Math.floor(data.timeInSeconds / 3600)
      this.examMinutes = (data.timeInSeconds % 3600) / 60;
    });
  }

  examModel = new Exam();
  examId: any;
  newQuestionType = "single";
  difficultyLevels: any;
  examHours!: number;
  examMinutes!: number;

  addNewQuestion(questionType: string) {
    let quest = this.examService.getNewQuestion(questionType);
    this.examModel.questions.push(quest);
  }

  addNewAnswerToQuestion(question: Question) {
    let answer = new Answer(null, "", false);
    if (question.type === 'short') answer.correctness = true;
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
      this.examService.putExam(this.examModel).subscribe(console.log);
      alert("Egzamin został nadpisany!");
    } else alert("Nadpisywanie egzaminu nie powiodło się.");

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
