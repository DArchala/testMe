import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {ExamAttempt} from "../../models/exam-attempt";

@Component({
  selector: 'app-my-exam-attempts',
  templateUrl: './my-exam-attempts.component.html',
  styleUrls: ['./my-exam-attempts.component.css']
})
export class MyExamAttemptsComponent {


  constructor(private _liveAnnouncer: LiveAnnouncer) {
  }

  displayedColumns: string[] = ['id', 'examName', 'examTime', 'examUserTime', 'examUserPoints', 'examMaxPoints', 'startTimeDate', 'endTimeDate', 'examDifficultyLevel'];

  ELEMENT_DATA: ExamAttempt[] = [
    {id: 1, examName: 'Hydrogen', examTime: 1, examUserTime: 5, examUserPoints: 1 , examMaxPoints: 1, startTimeDate: new Date(), endTimeDate: new Date(), examDifficultyLevel: 'MEDIUM'},
    {id: 2, examName: 'Helium',   examTime: 1, examUserTime: 5, examUserPoints: 1, examMaxPoints: 1, startTimeDate: new Date(), endTimeDate: new Date(), examDifficultyLevel: 'MEDIUM'},
  ];

  dataSource = new MatTableDataSource(this.ELEMENT_DATA);

  // announceSortChange(sortState: Sort) {
  //   if (sortState.direction)
  //     this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
  //   else
  //     this._liveAnnouncer.announce('Sorting cleared');
  //
  // }
}
