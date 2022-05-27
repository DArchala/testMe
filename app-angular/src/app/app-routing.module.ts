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

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'exams', component: ExamsComponent},
  {path: 'exams/exam/:id', component: ExamComponent},
  {path: 'exams/edit/:id', component: EditExamComponent},
  {path: 'new-exam', component: NewExamComponent},
  {path: '**', component: PageNotFoundComponent},
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule {

}
