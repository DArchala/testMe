import {Question} from "../question";
import {Answer} from "../answer";

export class ShortAnswerQuestion extends Question {

  override id: any;
  override content: string;
  override answers: Answer[];
  override type: string;
  override userAnswer: string;

  constructor(content: string, answers: Answer[], type: string, userAnswer: string) {
    super(content, answers, type, userAnswer);
    this.content = content;
    this.answers = answers;
    this.type = type;
    this.userAnswer = userAnswer;
  }
}
