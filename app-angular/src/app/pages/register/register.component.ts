import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  userModel = new User();

  constructor(private userService: UserService) {
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

  userRegisterForm = new FormGroup({
    username: this.usernameControl,
    email: this.emailControl,
    password: this.passwordControl,
    passwordRepeat: this.passwordRepeatControl
  });

  registerUser() {
    if (this.userRegisterForm.invalid || this.passwordControl.value != this.passwordRepeatControl.value) {
      alert("Sprawdź poprawność wpisanych informacji.");
    } else {
      this.userModel.username = this.usernameControl.value;
      this.userModel.email = this.emailControl.value;
      this.userModel.password = this.passwordControl.value;
      this.userService.registerNewUser(this.userModel).subscribe(console.log);
      alert("Wiadomość e-mail z linkiem potwierdzającym został wysłany. Sprawdź skrzynkę pocztową.");
    }
  }
}
