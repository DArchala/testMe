import {Question} from "./question";

export class Exam {
  id: number | any;
  difficultyLevel!: string;
  examName: string | any;
  examQuestionsNumber: number | any;
  questions: Question[] | any;
}
