import {Question} from "./question";

export class Exam {
  id!: number;
  difficultyLevel!: string;
  examName: string | any;
  examQuestionsNumber: number | any;
  questions: Question[] | any;

}
