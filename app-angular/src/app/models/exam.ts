import {Question} from "./question";

export class Exam {
  id: number | any;
  questions: Question[] | any;
  examName: string | any;
  difficultyLevel!: string;
  timeInSeconds!: number;
}
