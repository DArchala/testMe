import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {ActivatedRoute} from "@angular/router";
import {Answer} from "../../models/answer";
import {Question} from "../../models/question";

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit {


  exam!: Exam;
  responseExamPoints!: any;
  examStarted = false;
  interval: any;
  examId: number | any;
  maxExamTime!: number;
  examTimeLeft!: number;
  userLastExamTime!: number;

  constructor(private examService: ExamsService,
              private route: ActivatedRoute) {
    this.examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(this.examId).subscribe(data => {
      this.exam = data;
      this.maxExamTime = data.timeInSeconds;
      this.examTimeLeft = data.timeInSeconds;
    });
  }

  ngOnInit() {

  }


  startTest() {
    this.examService.getExamById(this.examId).subscribe(data => this.exam = data);
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
    this.examService.postExamToCheckCorrectness(this.exam).subscribe(data => this.responseExamPoints = data);
    this.userLastExamTime = this.maxExamTime - this.examTimeLeft;
    alert("Egzamin zakończony!");
    this.examStarted = false;
    this.examService.getExamById(this.examId).subscribe(data => this.examTimeLeft = data.timeInSeconds);
  }

  uncheckOtherAnswers(answer: Answer, question: Question) {
    question.answers.forEach(answer => answer.correctness = false);
    answer.correctness = true;
  }

}
