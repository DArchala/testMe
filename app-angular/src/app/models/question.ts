import {Answer} from "./answer";

export class Question {
  private _id: number | any;
  private _content!: string;
  private _answers!: Answer[];

  get id(): any {
    return this._id;
  }

  set id(value: any) {
    this._id = value;
  }

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  get answers(): Answer[] {
    return this._answers;
  }

  set answers(value: Answer[]) {
    this._answers = value;
  }

  setAllAnswersFalse() {
    this._answers.forEach(answer => answer.correctness = false);
  }
}
