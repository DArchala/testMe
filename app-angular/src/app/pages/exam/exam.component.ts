import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";

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
  timeLeft: number = 10;
  interval: any;

  ngOnInit() {
    const examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(examId).subscribe(data => this.exam = data);
  }

  startTest() {
    console.log("startTest()");
    this.examStarted = true;
    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      console.log("this.timeLeft -> " + this.timeLeft);
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
    this.timeLeft = 10;
  }

}
