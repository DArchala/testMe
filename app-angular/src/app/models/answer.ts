export class Answer {
  id: any;
  content!: string;
  correctness!: boolean;

  constructor(content: string, correctness: boolean) {
    this.content = content;
    this.correctness = correctness;
  }

  toString() {
    return "id=" + this.id + ", content=" + this.content + ", correctness=" + this.correctness;
  }
}
