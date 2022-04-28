export class Answer {
  private _id!: number;
  private _content!: string;
  private _correctness!: boolean;

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  get correctness(): boolean {
    return this._correctness;
  }

  set correctness(value: boolean) {
    this._correctness = value;
  }
}
