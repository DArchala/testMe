import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent {

  constructor(private userService: UserService) {
  }

  turnSpinnerOn = false;

  emailControl = new FormControl('', [
    Validators.required,
    Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
  ]);

  emailFormGroup = new FormGroup({
    email: this.emailControl
  });

  sendPasswordResetToken() {
    this.turnSpinnerOn = true;
    if (this.emailFormGroup.invalid) {
      alert("Sprawdź poprawność wprowadzonych danych.");
    } else {
      this.userService.postPasswordResetEmailRequest(this.emailControl.value).subscribe(
        () => {
        },
        error => {
          switch (error.status) {
            case 200:
              alert(error.error.text);
              break;
            default:
              alert(error.error);
              break
          }
          this.turnSpinnerOn = false;
        }
      );
    }
  }
}
