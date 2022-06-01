import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {DialogService} from "../../services/dialog.service";


@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.css']
})
export class ExamsComponent implements OnInit {

  constructor(private examService: ExamsService, private dialogService: DialogService) {
  }

  exams: Exam[] = [];

  ngOnInit(): void {
    this.examService.getExams().subscribe(exam => this.exams = exam);
  }

  deleteExam(information: string, id: any) {
    const answer = this.dialogService.getDialog(information);

    answer.afterClosed().subscribe(accept => {
      if (accept) this.examService.deleteExam(id);
    });

  }

}
