import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent {

  constructor(private route: ActivatedRoute, private userService: UserService) {
    this.token = this.route.snapshot.paramMap.get('token');
    this.userService.confirmAccountActivationByToken(this.token).subscribe(
      () => {
      }, error => {
        switch (error.status) {
          case 200:
            this.info = error.error.text;
            break;
          default:
            this.info = error.error;
            break
        }
      }
    );
  }

  token: any;
  info: any;

}
