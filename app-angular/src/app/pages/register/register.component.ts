import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {InfoStatic} from "../../support/info-static";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(private userService: UserService) {
  }

  userModel = new User();
  turnSpinnerOn = false;

  usernameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(60)
  ]);

  emailControl = new FormControl('', [
    Validators.required,
    Validators.pattern(InfoStatic.staticEmailPattern)
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

  userRegisterForm = new FormGroup({
    username: this.usernameControl,
    email: this.emailControl,
    password: this.passwordControl,
    passwordRepeat: this.passwordRepeatControl
  });

  registerUser() {
    if (this.userRegisterForm.invalid || this.passwordControl.value != this.passwordRepeatControl.value) {
      alert(InfoStatic.checkFormData);
    } else {
      this.turnSpinnerOn = true;
      this.userModel.username = this.usernameControl.value;
      this.userModel.email = this.emailControl.value;
      this.userModel.password = this.passwordControl.value;
      this.userService.registerNewUser(this.userModel).subscribe(
        () => {
        }, (error: HttpErrorResponse) => {
          this.turnSpinnerOn = false;
          if(error.status === 400) alert(error.error);
          else alert(error.error.text);
        }
      );
    }
  }
}
