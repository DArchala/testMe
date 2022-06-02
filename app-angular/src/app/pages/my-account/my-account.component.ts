import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../models/user";
import {DialogService} from "../../services/dialog.service";

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
      }
    );
  }

  usernameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(60)
  ]);

  emailControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
  ]);

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

  userOverwriteDataForm = new FormGroup({
    username: this.usernameControl,
    email: this.emailControl,
    password: this.passwordControl,
    passwordRepeat: this.passwordRepeatControl
  });

  saveUser(information: string) {
    const answer = this.dialogService.getDialog(information);
    answer.afterClosed().subscribe(accept => {
      if (accept) this.overwriteUserData();
    });

  }

  overwriteUserData() {
    if (this.userOverwriteDataForm.valid
      && this.passwordControl.value === this.passwordRepeatControl.value) {
      let user = new User();
      user.username = this.usernameControl.value;
      user.email = this.emailControl.value;
      user.password = this.passwordControl.value;
      this.userService.updateUser(user);
    } else alert("Sprawdź poprawność wpisanych danych.")
  }
}
