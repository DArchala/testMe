import {Answer} from "./answer";

export abstract class Question {
  id: number | any;
  content!: string;
  answers!: Answer[];
  type!: string;
  userAnswer!: string;
}
