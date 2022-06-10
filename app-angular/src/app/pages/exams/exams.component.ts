import {Component, OnInit} from '@angular/core';
import {ExamsService} from "../../services/exams.service";
import {Exam} from "../../models/exam";
import {DialogService} from "../../services/dialog.service";


@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.css']
})
export class ExamsComponent {

  constructor(private examService: ExamsService, private dialogService: DialogService) {
    this.examService.getExams().subscribe(exams => {
      if(exams.length === 0) this.lackOfExams = true;
      this.exams = exams;
    });
  }

  exams: Exam[] = [];
  lackOfExams!: boolean;

  deleteExam(information: string, id: any) {
    const answer = this.dialogService.getDialog(information);

    answer.afterClosed().subscribe(accept => {
      if (accept) this.examService.deleteExam(id);
    });

  }

}
