import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {User} from "../../models/user";
import {MatSort, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {RoleEnum} from "../../models/role-enum";
import {AcceptRole} from "../../models/accept-role";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {DialogService} from "../../services/dialog.service";
import {AuthenticationService} from "../../services/authentication.service";


@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {

  constructor(private _liveAnnouncer: LiveAnnouncer, public userService: UserService, private router: Router,
              private dialogService: DialogService, private authService: AuthenticationService) {
    this.userService.getUsers().subscribe((users:User[]) => {
      if(users.length === 0) this.lackOfUsers = true;
      this.dataSource = new MatTableDataSource<User>(users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.paginator._intl.itemsPerPageLabel="Users per page: ";
    });
  }

  displayedColumns: string[] = ['id', 'username', 'email', 'role'];
  settingMyselfAsUser = false;
  dataSource = new MatTableDataSource<User>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  lackOfUsers!: boolean;

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

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
    const answer = this.dialogService.getDialog("Do you want to delete user: " + user.username + "?");
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
    const answer = this.dialogService.getUserNewRoleDialog("Choose new role for user: " + user.username, roles);
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
