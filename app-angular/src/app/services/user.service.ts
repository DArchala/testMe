import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) {
  }

  registerNewUser(user: User) {
    return this.httpClient.post<User>(this.url + `/register`, user).pipe(tap(console.log));
  }

  findUserByUsername(username: any) {
    return this.httpClient.post<User>(this.url + `/findByUsername`, username).pipe(tap(console.log));
  }

  updateUserRole(user: User) {
    return this.httpClient.put<User>(this.url + `/updateUser/role`, user).pipe(tap(console.log));
  }

  confirmToken(tokenValue: string) {
    return this.httpClient.get<any>(this.url + `/token?value=` + tokenValue).pipe(tap(console.log));
  }

  getUsers() {
    return this.httpClient.get<User[]>(this.url + `/users`).pipe(tap(console.log));
  }

  deleteUser(userID: any) {
    return this.httpClient.delete<any>(this.url + `/users/delete/` + userID).pipe(tap(console.log));
  }

  getRoles() {
    return this.httpClient.get<string[]>(this.url + `/getRoles`).pipe(tap(console.log));
  }

  updateUser(user: User) {
    // return this.httpClient.put<User>(this.url + `/saveUserChanges`, user).pipe(tap(console.log));
  }
}
