import {Question} from "./question";

export class Exam {
  id: any;
  questions!: Question[];
  examName!: string;
  difficultyLevel!: string;
  timeInSeconds!: number;
}
