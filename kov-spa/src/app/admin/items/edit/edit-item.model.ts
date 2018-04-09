export class EditItemModel {

  constructor(public name?: string, public type?: string,
              public bonus?: number, public price?: number,
              public oldImage?: string, public image?: File) {
  }
}
