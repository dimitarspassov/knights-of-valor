export class EditJobModel {
  constructor(public name?: string, public minutes?: number,
              public salary?: number, public oldImage?: string,
              public image?: File) {
  }
}
