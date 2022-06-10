import {Injectable} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../pages/dialog/dialog.component";
import {UserOptionsDialogComponent} from "../pages/users-list/user-options-dialog/user-options-dialog.component";
import {UserNewRoleDialogComponent} from "../pages/users-list/user-new-role-dialog/user-new-role-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) {
  }

  getDialog(information: string) {
    return this.dialog.open(DialogComponent, {
      minHeight: '200px',
      minWidth: '200px',
      width: '15%',
      height: 'auto',
      maxHeight: '300px',
      data: {
        accept: false,
        info: information,
      }
    });
  }

  getUserOptionsDialog() {
    return this.dialog.open(UserOptionsDialogComponent, {
      minHeight: '200px',
      minWidth: '200px',
      width: 'auto',
      height: 'auto',
      maxHeight: '300px',
      data: {
        userOption: 3,
      }
    });
  }

  getUserNewRoleDialog(information: string, roles: string[]) {
    return this.dialog.open(UserNewRoleDialogComponent, {
      minHeight: '200px',
      minWidth: '200px',
      width: 'auto',
      height: 'auto',
      maxHeight: '300px',
      data: {
        information: information,
        roles: roles
      }
    });
  }
}
