import {Component} from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";
import {DialogService} from "./services/dialog.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app-angular';

  constructor(public authService: AuthenticationService, private dialogService: DialogService,
              private router: Router) {
  }

  logMeOut(information: string) {
    const answer = this.dialogService.getDialog(information);
    answer.afterClosed().subscribe(accept => {
      if (accept) this.router.navigate(['logout']);
    });
  }

}
