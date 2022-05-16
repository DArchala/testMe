import {Question} from "./question";

export class Exam {
  id: any;
  questions!: Question[];
  examName!: string;
  difficultyLevel!: string;
  timeInSeconds!: number;

  toString(){
    return "id=" + this.id
      +", questions=" + this.questions
      +", examName=" + this.examName
      +", difficultyLevel=" + this.difficultyLevel
      +", timeInSeconds=" + this.timeInSeconds;
  }
}
