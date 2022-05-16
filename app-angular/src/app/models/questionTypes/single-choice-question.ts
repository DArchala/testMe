import {Question} from "../question";
import {Answer} from "../answer";

export class SingleChoiceQuestion extends Question {

  constructor(content: string, answers: Answer[], type: string, userAnswer: string) {
    super(content, answers, type, userAnswer);
  }
}
