import {Answer} from "./answer";

export abstract class Question {
  id: any;
  content!: string;
  answers!: Answer[];
  type!: string;
  userAnswer!: string;

  constructor(content: string, answers: Answer[], type: string, userAnswer: string) {
    this.content = content;
    this.answers = answers;
    this.type = type;
    this.userAnswer = userAnswer;
  }
}
