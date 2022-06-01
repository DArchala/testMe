import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

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
    if (this.userLoginForm.invalid) {
      alert("Sprawdź poprawność wpisanych informacji.");
    } else {
      this.authService.authenticate(this.usernameControl.value, this.passwordControl.value).subscribe(
        data => {
          this.router.navigate(["/home"]);
        },
        error => {
          alert("Error = " + error);
        }
      );
    }
  }

}
