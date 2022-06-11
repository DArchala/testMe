import {Component} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {ActivatedRoute, Router} from "@angular/router";
import {Answer} from "../../models/answer";
import {Question} from "../../models/question";
import {DialogService} from "../../services/dialog.service";
import {ExamDateTime} from "../../models/exam-date-time";
import {ExamForm} from "../../models/exam-form";

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent {

  examForm = new ExamForm();
  exam = new Exam();
  examDateTime = new ExamDateTime();

  examId: any;
  examStarted = false;
  examTimeLeft!: number;
  examMaxPoints!: number;
  maxExamTime!: number;
  responseExamPoints = 0;
  userLastExamTime = 0;
  interval: any;

  constructor(private examService: ExamsService, private router: Router,
              private route: ActivatedRoute, private dialogService: DialogService) {
    this.examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(this.examId).subscribe(data => {
      this.exam = data;
      this.maxExamTime = data.timeInSeconds;
      this.examTimeLeft = data.timeInSeconds;
    }, error => {
      if(error.status === 404) this.router.navigate(['exams']);
    });
    this.examService.postExamGetMaxPoints(this.examId).subscribe(data => this.examMaxPoints = data);
  }


  startTest() {
    this.examService.getExamById(this.examId).subscribe(data => this.exam = data);
    this.examDateTime.startDateTime = new Date();
    this.examStarted = true;
    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if (this.examTimeLeft > 0) {
        this.examTimeLeft--;
      } else {
        clearInterval(this.interval);
        this.finishTest();
      }
    }, 1000);
  }

  finishTest() {
    clearInterval(this.interval);
    this.userLastExamTime = this.maxExamTime - this.examTimeLeft;
    this.examDateTime.endDateTime = new Date();
    this.examForm.exam = this.exam;
    this.examForm.examDateTime = this.examDateTime;
    this.examForm.examDateTime.userExamTime = this.userLastExamTime;
    this.examService.postExamToCheckCorrectness(this.examForm).subscribe(data => this.responseExamPoints = data);
    alert("Exam ended!");
    this.examStarted = false;
    this.examService.getExamById(this.examId).subscribe(data => this.examTimeLeft = data.timeInSeconds);
  }

  selectAnswer(answer: Answer, question: Question) {
    if(question.type === 'single') {
      question.answers.filter(a => a.id != answer.id).forEach(answer => answer.correctness = false);
    }
  }

  getPercentageExamScore(examScore: number, maxPoints: number) {
    let score = Math.round((examScore / maxPoints) * 100);
    return score + "%";
  }

  finishTestByClick(info: string) {
    if (!this.doUserFillAllAnswers())
      info = "Exam still contain questions without your answer. Do you really want to finish exam now?";
    const answer = this.dialogService.getDialog(info);
    answer.afterClosed().subscribe(accept => {
      if (accept) this.finishTest();
    });
  }

  doUserFillAllAnswers() {
    let count = 0;
    this.exam.questions.forEach(question => {
      if (question.type != 'short' && question.answers.filter(answer => answer.correctness).length < 1) count++;
      if (question.type === 'short' && question.userAnswer.trim() === '') count++;
    });
    return count === 0;
  }
}
