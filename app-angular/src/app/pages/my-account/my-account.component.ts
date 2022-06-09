import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {DialogService} from "../../services/dialog.service";
import {PasswordChangeRequest} from "../../models/password-change-request";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent {

  constructor(private userService: UserService, private authService: AuthenticationService,
              private dialogService: DialogService) {
    this.userService.findUserByUsername(authService.getUsername()).subscribe(
      data => {
        this.usernameControl.patchValue(data.username);
        this.emailControl.patchValue(data.email);
        this.myRole = data.role;
      }
    );
  }

  myRole!: string;
  turnSpinnerOn = false;

  usernameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(60)
  ]);

  emailControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
  ]);

  currentPasswordControl = new FormControl('', [
    Validators.required,
  ]);

  newPasswordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30)
  ]);

  newPasswordRepeatControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30)
  ]);

  changePasswordForm = new FormGroup({
    username: this.usernameControl,
    email: this.emailControl,
    password: this.newPasswordControl,
    passwordRepeat: this.newPasswordRepeatControl
  });

  saveUser(information: string) {
    const answer = this.dialogService.getDialog(information);
    answer.afterClosed().subscribe(accept => {
      if (accept) this.overwriteUserData();
    });
  }

  overwriteUserData() {
    if (this.changePasswordForm.valid
      && this.newPasswordControl.value === this.newPasswordRepeatControl.value) {
      this.turnSpinnerOn= true;
      let passChangeReq = new PasswordChangeRequest();
      passChangeReq.username = this.usernameControl.value;
      passChangeReq.email = this.emailControl.value;
      passChangeReq.currentPassword = this.currentPasswordControl.value;
      passChangeReq.newPassword = this.newPasswordControl.value;
      this.userService.postPasswordChangeRequest(passChangeReq).subscribe(
        () => {
        }, error => {
          switch (error.status) {
            case 200:
              alert("Password was changed.");
              window.location.reload();
              break;
            default:
              this.turnSpinnerOn = false;
              alert("Error " + error.status + ": " + error.error);
              break;
          }
        }
      );
    } else alert("Check form data correctness.")
  }
}
