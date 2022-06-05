import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {ExamAttempt} from "../../models/exam-attempt";
import {ExamAttemptsService} from "../../services/exam-attempts.service";

@Component({
  selector: 'app-my-exam-attempts',
  templateUrl: './my-exam-attempts.component.html',
  styleUrls: ['./my-exam-attempts.component.css']
})
export class MyExamAttemptsComponent {

  constructor(private examAttemptsService: ExamAttemptsService,private _liveAnnouncer: LiveAnnouncer) {
    this.examAttemptsService.getMyExamAttempts().subscribe(next => {
      next.forEach(examAttempt => {
        this.myExamAttempts.push(examAttempt);
      });
    });
  }

  displayedColumns: string[] = ['id', 'examName', 'examTime', 'examUserTime', 'examUserPoints', 'examMaxPoints', 'startTimeDate', 'endTimeDate', 'examDifficultyLevel'];

  myExamAttempts: ExamAttempt[] = [];

  dataSource = new MatTableDataSource(this.myExamAttempts);

}
