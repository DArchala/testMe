import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {

  constructor(private userService: UserService, private router: Router) {
    this.userService.getUsers().subscribe(next => {
      next.forEach((user: User) => {
        this.usersList.push(user);
      });
    }, error => {
      if(error.status === 403) {
        alert("Brak uprawnień");
        this.router.navigate(['home']);
      }
    });
  }

  displayedColumns: string[] = ['id', 'username', 'email', 'role'];

  usersList: User[] = [];

  dataSource = new MatTableDataSource(this.usersList);

}
