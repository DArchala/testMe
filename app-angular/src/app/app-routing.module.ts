import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./pages/home/home.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";
import {ExamsComponent} from "./pages/exams/exams.component";
import {ExamComponent} from "./pages/exam/exam.component";
import {NewExamComponent} from "./pages/new-exam/new-exam.component";
import {EditExamComponent} from "./pages/edit-exam/edit-exam.component";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {LogoutComponent} from "./pages/logout/logout.component";
import {AuthGuard} from "./support/auth.guard";
import {MyAccountComponent} from "./pages/my-account/my-account.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {MyExamAttemptsComponent} from "./pages/my-exam-attempts/my-exam-attempts.component";
import {PasswordResetComponent} from "./pages/password-reset/password-reset.component";
import {PasswordResetNewComponent} from "./pages/password-reset/password-reset-new/password-reset-new.component";
import {UsersListComponent} from "./pages/users-list/users-list.component";

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home',                      component: HomeComponent,             canActivate: [AuthGuard]},
  {path: 'login',                     component: LoginComponent},
  {path: 'register',                  component: RegisterComponent},
  {path: 'logout',                    component: LogoutComponent,           canActivate: [AuthGuard]},
  {path: 'my-account',                component: MyAccountComponent,        canActivate: [AuthGuard]},
  {path: 'exams',                     component: ExamsComponent,            canActivate: [AuthGuard]},
  {path: 'exams/exam/:id',            component: ExamComponent,             canActivate: [AuthGuard]},
  {path: 'exams/edit/:id',            component: EditExamComponent,         canActivate: [AuthGuard]},
  {path: 'new-exam',                  component: NewExamComponent,          canActivate: [AuthGuard]},
  {path: 'activate-account/:token',   component: ActivateAccountComponent},
  {path: 'my-exam-attempts',          component: MyExamAttemptsComponent,   canActivate: [AuthGuard]},
  {path: 'users',                     component: UsersListComponent,        canActivate: [AuthGuard]},
  {path: 'password-reset',            component: PasswordResetComponent},
  {path: 'password-reset-new/:token', component: PasswordResetNewComponent},
  {path: '**',                        component: PageNotFoundComponent},
]

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
})

export class AppRoutingModule {

}
