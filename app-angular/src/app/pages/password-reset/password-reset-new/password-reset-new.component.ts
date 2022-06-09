import {Component} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../../models/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-password-reset-new',
  templateUrl: './password-reset-new.component.html',
  styleUrls: ['./password-reset-new.component.css']
})
export class PasswordResetNewComponent {

  constructor(private userService: UserService, private route: ActivatedRoute,
              private router: Router) {
    this.tokenValue = this.route.snapshot.paramMap.get('token');
    this.userService.confirmResetPasswordByToken(this.tokenValue).subscribe(
      next => {
        this.user = next;
        console.log(this.user);
      },
      error => {
        switch (error.status) {
          case 200:
            this.showNewPasswordPanel = true;
            break;
          default:
            alert(error.error.text);
            this.router.navigate(['login']);
            break;
        }
      }
    );
  }

  turnSpinnerOn = false;
  showNewPasswordPanel = false;
  tokenValue: any;
  response: any;
  user!: User;

  passwordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30)
  ]);

  passwordRepeatControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30)
  ]);

  setNewPasswordForm = new FormGroup({
    password: this.passwordControl,
    passwordRepeat: this.passwordRepeatControl
  });

  setNewResetedPassword() {
    if (this.setNewPasswordForm.invalid
      || this.passwordControl.value != this.passwordRepeatControl.value) {
      alert("Sprawdź poprawność wprowadzonych danych.");
    } else {
      this.user.password = this.passwordControl.value;
      this.userService.putUserWithNewPassword(this.user).subscribe(
        () => {
        },
        error => {
          switch (error.status) {
            case 200:
              alert("Hasło zostało zmienione, możesz teraz się zalogować z jego użyciem.");
              this.router.navigate(['login']);
              break;
            default:
              alert(error.error.text);
              break;
          }
        }
      );
    }
  }

}
