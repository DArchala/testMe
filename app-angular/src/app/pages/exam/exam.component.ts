import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {ActivatedRoute} from "@angular/router";
import {Question} from "../../models/question";
import {Answer} from "../../models/answer";

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
  examStarted = false;
  timeLeft: number = 3600;
  interval: any;

  ngOnInit() {
    const examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(examId).subscribe(data => this.exam = data);
  }

  startTest() {
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
    alert("Egzamin zakończony!");
    this.examStarted = false;
    this.timeLeft = 3600;
  }

  printAnswer(answer: any) {
    console.log(answer);
  }
}
