import {Injectable} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../pages/dialog/dialog.component";

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
      height: '30%',
      maxHeight: '200px',
      data: {
        accept: false,
        info: information,
      }
    });
  }
}
