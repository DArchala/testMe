import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  userModel = new User();

  constructor(private userService: UserService) { }

  registerUser() {
    this.userService.register(this.userModel).subscribe(console.log);
  }
}
