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

  constructor(private examService: ExamsService,
              private route: ActivatedRoute) {
  }

  exam!: Exam;
  responseExamPoints!: any;
  examStarted = false;
  timeLeft: number = 3600;
  interval: any;
  examId: number | any;
  userExamTime!: number;

  ngOnInit() {
    this.examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(this.examId).subscribe(data => this.exam = data);
  }

  startTest() {
    this.examService.getExamById(this.examId).subscribe(data => this.exam = data);
    this.examStarted = true;
    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        clearInterval(this.interval);
        this.finishTest();
      }
    }, 1000);
  }

  finishTest() {
    clearInterval(this.interval);
    this.userExamTime = 3600 - this.timeLeft;
    this.examService.postExamToCheckCorrectness(this.exam).subscribe(data => this.responseExamPoints = data);
    alert("Egzamin zakończony!");
    this.examStarted = false;
    this.timeLeft = 3600;
  }

  uncheckOtherAnswers(answer: Answer, question: Question) {
    question.answers.forEach(answer => answer.correctness = false);
    answer.correctness = true;
  }

  calcExamUserTime() {

  }
}
