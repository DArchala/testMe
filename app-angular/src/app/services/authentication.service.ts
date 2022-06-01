import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {
  }

  authenticate(username: string, password: string): Observable<void> {
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    return this.httpClient
      .post<any>(`http://localhost:8080/login`, formData)
      .pipe(
        map(user => {
          sessionStorage.setItem('username', username);
          const tokenStr = 'Bearer ' + user.token;
          sessionStorage.setItem('token', tokenStr);
          return user;
        })
      );
  }

  isUserLoggedIn() {
    const username = sessionStorage.getItem('username');
    return username != null;
  }

  clear() {
    sessionStorage.removeItem('username');
  }

  logOut(): Observable<void> {
    sessionStorage.removeItem('username');
    return this.httpClient.post<any>(`http://localhost:8080/logout`, null).pipe(tap(console.log));
  }

  getUsername() {
    const username = sessionStorage.getItem('username');
    if (username !== null)
      return sessionStorage.getItem('username');
    else return '';
  }

}
