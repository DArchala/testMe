import {Question} from "../question";
import {Answer} from "../answer";

export class MultipleChoiceQuestion extends Question {

  constructor(id: any, content: string, answers: Answer[], type: string, userAnswer: string) {
    super(id, content, answers, type, userAnswer);
  }
}
