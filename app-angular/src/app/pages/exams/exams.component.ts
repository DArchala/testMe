import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";


@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.css']
})
export class ExamsComponent implements OnInit {

  constructor(private examService: ExamsService) { }

  exams!: Exam[];

  ngOnInit(): void {
    this.examService.getExams().subscribe(exam => this.exams = exam);
  }

}
