import {Pipe, PipeTransform} from '@angular/core';
import {min} from "rxjs";

@Pipe({
  name: 'examAttemptConverter'
})
export class ExamAttemptConverterPipe implements PipeTransform {

  transform(date: Date): string {
    let actDate = new Date(date);
    let day = actDate.getUTCDate() + "";
    let month = (actDate.getMonth() + 1) + "";
    let hour = actDate.getHours() + "";
    let minutes = actDate.getMinutes() + "";
    let seconds = Math.floor(actDate.getSeconds()) + "";
    let year = actDate.getFullYear() + "";

    if (actDate.getUTCDate().toString().length === 1) day = "0" + actDate.getUTCDate();
    if ((actDate.getMonth() + 1).toString().length === 1) month = "0" + (actDate.getMonth() + 1);
    if (actDate.getHours().toString().length === 1) hour = "0" + actDate.getHours();
    if (actDate.getMinutes().toString().length === 1) minutes = "0" + actDate.getMinutes();
    if (actDate.getSeconds().toString().length === 1) seconds = "0" + actDate.getSeconds();
    return day + "-" + month + "-" + year + " " + hour + ":" + minutes + ":" + seconds;
  }

}
