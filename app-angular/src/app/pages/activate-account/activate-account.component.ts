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
    this.userService.confirmToken(this.token).subscribe(
      data => {

      }, error => {
        this.info = error.error.text;
      }
    );

  }

  token: any;
  info: any;

}
