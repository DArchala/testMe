import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {AcceptRole} from "../../../models/accept-role";

@Component({
  selector: 'app-user-new-role-dialog',
  templateUrl: './user-new-role-dialog.component.html',
  styleUrls: ['./user-new-role-dialog.component.css']
})
export class UserNewRoleDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.acceptRole.accept = false;
  }

  acceptRole = new AcceptRole();

  acceptNewRole() {
    this.acceptRole.accept = true;
  }

  cancelRole() {
    this.acceptRole.accept = false;
  }

  setRole(role: string) {
    this.acceptRole.role = role;
    console.log(this.acceptRole);
  }
}
