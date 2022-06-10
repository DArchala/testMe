import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatSort, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {ExamAttempt} from "../../models/exam-attempt";
import {ExamAttemptsService} from "../../services/exam-attempts.service";
import {User} from "../../models/user";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-my-exam-attempts',
  templateUrl: './my-exam-attempts.component.html',
  styleUrls: ['./my-exam-attempts.component.css']
})
export class MyExamAttemptsComponent {

  constructor(private examAttemptsService: ExamAttemptsService,
              private _liveAnnouncer: LiveAnnouncer,) {
    this.examAttemptsService.getMyExamAttempts().subscribe((examAttempts: ExamAttempt[]) => {
      if(examAttempts.length === 0) this.lackOfExamAttempts = true;
      this.dataSource = new MatTableDataSource<ExamAttempt>(examAttempts);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.paginator._intl.itemsPerPageLabel="Exam attempts per page: ";
    });
  }

  displayedColumns: string[] = ['id', 'examName', 'examTime', 'examUserTime', 'examUserPoints', 'examMaxPoints', 'startTimeDate', 'endTimeDate', 'examDifficultyLevel'];
  dataSource = new MatTableDataSource<ExamAttempt>();
  lackOfExamAttempts = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

}
