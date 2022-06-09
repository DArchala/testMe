import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {DialogService} from "../../services/dialog.service";
import {AcceptRole} from "../../models/accept-role";
import {RoleEnum} from "../../models/role-enum";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {

  constructor(private userService: UserService, private router: Router,
              private dialogService: DialogService, private authService: AuthenticationService) {
    this.userService.getUsers().subscribe(next => {
      next.forEach((user: User) => {
        this.usersList.push(user);
      });
    }, error => {
      if (error.status === 403) {
        alert("Brak uprawnień");
        this.router.navigate(['home']);
      }
    });
  }

  displayedColumns: string[] = ['id', 'username', 'email', 'role'];
  usersList: User[] = [];
  dataSource = new MatTableDataSource(this.usersList);
  settingMyselfAsUser = false;

  openUserOptions(user: User) {
    const answer = this.dialogService.getUserOptionsDialog();
    answer.afterClosed().subscribe(userOption => {
      switch (userOption) {
        case 1:
          this.deleteUser(user);
          break
        case 2:
          this.changeRole(user);
          break;
        case 3:
          return;
      }
    });
  }

  deleteUser(user: User) {
    const answer = this.dialogService.getDialog("Czy chcesz usunąć użytkownika: " + user.username + "?");
    answer.afterClosed().subscribe(accept => {
      if (accept) this.userService.deleteUser(user.id).subscribe(
        () => {
        }, error => {
          switch (error.status) {
            case 200:
              alert(error.status + ": " + error.error.text);
              if (user.username === this.authService.getUsername()) {
                this.authService.clear();
                this.router.navigate(['login']);
              }
              window.location.reload();
              break;
            default:
              alert("Error " + error.status + ": " + error.error);
              break;
          }
        },
      );
    });
  }

  changeRole(user: User) {
    let roles: string[] = [];
    this.userService.getRoles().subscribe((data: RoleEnum[]) => {
      data.forEach(role => {
        roles.push(role.toString());
      });
    });
    const answer = this.dialogService.getUserNewRoleDialog("Wybierz nową rolę użytkownika: " + user.username, roles);
    answer.afterClosed().subscribe((accept: AcceptRole) => {
      if (accept.accept) {
        let userDTO = this.getUserDTOFromUser(user);
        userDTO.role = accept.role;
        if (userDTO.username === this.authService.getUsername() && accept.role === 'USER') this.settingMyselfAsUser = true;

        this.userService.putUserWithNewRole(userDTO).subscribe(
          () => {
          }, error => {
            switch (error.status) {
              case 200:
                alert(error.error.text);
                if (this.settingMyselfAsUser) this.router.navigate(['my-account']);
                break;
              default:
                alert("Error " + error.status + ": " + error.error);
                break;
            }
          });
      } else return;
    });
  }

  private getUserDTOFromUser(user: User) {
    let userDTO = new User();
    userDTO.id = user.id;
    userDTO.username = user.username;
    userDTO.email = user.email;
    userDTO.isEnabled = user.isEnabled;
    userDTO.password = user.password;
    return userDTO;
  }
}
