import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-user-options-dialog',
  templateUrl: './user-options-dialog.component.html',
  styleUrls: ['./user-options-dialog.component.css']
})
export class UserOptionsDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
  }

}
