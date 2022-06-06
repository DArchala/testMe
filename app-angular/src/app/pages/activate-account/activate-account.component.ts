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
      () => {
      }, error => {
        console.log(error.error);
        switch (error.error) {
          case 'Your token has expired.':
            this.info = "Twój token wygasł. Utwórz nowe konto w panelu rejestracji.";
            break;
          case 'Token does not exist.':
            this.info = "Podany token nie istnieje.";
            break;
          case 'Token has no user.':
            this.info = "Podany token nie ma przypisanego użytkownika.";
            break;
          default:
            this.info = 'Nastąpił nieoczekiwany błąd.'
        }
        switch (error.error.text) {
          case 'User account is now enable. You can log in.':
            this.info = "Konto zostało aktywowane, możesz teraz się zalogować.";
            break;
          default:
            return;
        }
      }
    );

  }

  token: any;
  info: any;

}
