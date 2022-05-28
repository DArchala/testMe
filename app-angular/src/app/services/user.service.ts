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

}
