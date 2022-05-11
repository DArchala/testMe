import {Question} from "./question";

export class Exam {
  id: number | any;
  difficultyLevel!: string;
  examName: string | any;
  questions: Question[] | any;
  timeInSeconds!: number;
}
