import {Question} from "../question";
import {Answer} from "../answer";

export class ShortAnswerQuestion extends Question {

  constructor(id: any, content: string, answers: Answer[], type: string, userAnswer: string) {
    super(id, content, answers, type, userAnswer);
  }
}
