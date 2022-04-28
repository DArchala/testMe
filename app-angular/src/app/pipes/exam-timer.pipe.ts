import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'examTimer'
})
export class ExamTimerPipe implements PipeTransform {

  transform(seconds: number): string {
    let h = "00";
    let min = "00";
    let sec = "00";
    if(seconds >= 3600) {
      h = Math.floor(seconds/3600).toString();
      min = Math.floor(((seconds%3600)/60)).toString();
      sec = Math.floor((seconds%3600)%60).toString();
    } else if (seconds >= 60) {
      min = Math.floor(((seconds%3600)/60)).toString();
      sec = Math.floor((seconds%3600)%60).toString();
    } else sec = seconds.toString();
    if(h.length == 1) h = "0"+h;
    if(min.length == 1) min = "0"+min;
    if(sec.length == 1) sec = "0"+sec;
    return h+":"+min+":"+sec;
  }

}
