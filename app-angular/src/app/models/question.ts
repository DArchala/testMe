import {Answer} from "./answer";

export abstract class Question {
  id: any;
  content!: string;
  answers!: Answer[];
  type!: string;
  userAnswer!: string;

  protected constructor(id: any, content: string, answers: Answer[], type: string, userAnswer: string) {
    this.id = id;
    this.content = content;
    this.answers = answers;
    this.type = type;
    this.userAnswer = userAnswer;
  }
}
