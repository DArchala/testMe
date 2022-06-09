import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {User} from "../models/user";
import {tap} from "rxjs";
import {PasswordChangeRequest} from "../models/password-change-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/api/users";

  constructor(private httpClient: HttpClient) {
  }

  getUsers() {
    return this.httpClient.get<User[]>(this.url).pipe(tap(console.log));
  }

  findUserByUsername(username: any) {
    return this.httpClient.post<User>(this.url + `/findBy/username`, username).pipe(tap(console.log));
  }

  putUserWithNewRole(user: User) {
    return this.httpClient.put<User>(this.url + `/update/role`, user).pipe(tap(console.log));
  }

  putUserWithNewPassword(user: User) {
    return this.httpClient.put<User>(this.url + `/update/password`, user).pipe(tap(console.log));
  }

  deleteUser(userID: any) {
    return this.httpClient.delete<any>(this.url + `/delete/` + userID).pipe(tap(console.log));
  }

  registerNewUser(user: User) {
    return this.httpClient.post<User>(this.url + `/register`, user).pipe(tap(console.log));
  }

  confirmAccountActivationByToken(tokenValue: string) {
    return this.httpClient.get<HttpResponse<any>>(this.url + `/activateAccount/token?value=` + tokenValue).pipe(tap(console.log));
  }

  confirmResetPasswordByToken(tokenValue: string) {
    return this.httpClient.get<any>(this.url + `/passwordReset/token?value=` + tokenValue).pipe(tap(console.log));
  }

  getRoles() {
    return this.httpClient.get<string[]>(this.url + `/roles`).pipe(tap(console.log));
  }

  postPasswordChangeRequest(passwordRequest: PasswordChangeRequest) {
    return this.httpClient.post<User>(this.url + `/changeMyPassword`, passwordRequest).pipe(tap(console.log));
  }

  postPasswordResetEmailRequest(email: string) {
    return this.httpClient.post<any>(this.url + `/passwordResetRequest`, email).pipe(tap(console.log));
  }
}
