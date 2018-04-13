export class EditNeutralModel {

  constructor(public name?: string, public level?: number, public health?: number,
              public strength?: number, public defense?: number, public stamina?: number,
              public lootGold?: number, public type?: string,
              public image?: File, public oldImage?: string) {
  }
}
