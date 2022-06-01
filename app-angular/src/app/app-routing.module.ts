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

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent, canActivate: [AuthGuard]},
  {path: 'exams', component: ExamsComponent, canActivate: [AuthGuard]},
  {path: 'exams/exam/:id', component: ExamComponent, canActivate: [AuthGuard]},
  {path: 'exams/edit/:id', component: EditExamComponent, canActivate: [AuthGuard]},
  {path: 'new-exam', component: NewExamComponent, canActivate: [AuthGuard]},
  {path: '**', component: PageNotFoundComponent},
]

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
})

export class AppRoutingModule {

}
