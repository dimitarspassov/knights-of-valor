export class AddItemModel {

  constructor(public name?: string, public type: string = 'Weapon', public bonus?: number,
              public price?: number, public image?: File) {
  }
}
