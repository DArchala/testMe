import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit {

  constructor(private examService: ExamsService, private route: ActivatedRoute) { }

  exam!: Exam;
  examStarted = false;
  examEnded = false;

  ngOnInit() {
    const examId = this.route.snapshot.paramMap.get('id');
    this.examService.getExamById(examId).subscribe(data => this.exam = data);
  }

  startTest() {
    this.examStarted = true;
  }

  finishTest() {
    this.examEnded = true;
    alert("Egzamin zakończony!");
    this.examStarted = false;
    this.examEnded = false;
  }

}
