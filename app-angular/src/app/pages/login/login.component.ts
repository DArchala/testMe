import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {InfoStatic} from "../../support/info-static";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  turnSpinnerOn = false;

  usernameControl = new FormControl('', [
    Validators.required,
  ]);

  passwordControl = new FormControl('', [
    Validators.required,
  ]);

  userLoginForm = new FormGroup({
    username: this.usernameControl,
    password: this.passwordControl,
  });

  login() {
    this.turnSpinnerOn = true;
    if (this.userLoginForm.invalid) {
      alert(InfoStatic.checkFormData);
      this.turnSpinnerOn = false;
    } else {
      this.authService.authenticate(this.usernameControl.value, this.passwordControl.value).subscribe(
        () => {
          this.router.navigate(["/home"]);
        },
        error => {
          switch (error.status) {
            case 200:
              if (error.error.text.startsWith("<!DOCTYPE html>")) {
                alert("Wrong username or password.");
                this.turnSpinnerOn = false;
              }
              break;
            default:
              alert(error.error);
              break;
          }
        }
      );
    }
  }

  goToPasswordResetPage() {
    this.router.navigate(['password-reset']);
  }
}
