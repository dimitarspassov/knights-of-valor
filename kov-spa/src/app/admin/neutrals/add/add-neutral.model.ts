export class AddNeutralModel {

  constructor(public name?: string, public level?: number, public health?: number,
              public strength?: number, public defense?: number, public stamina?: number,
              public loot?: number, public type: string = 'Regular', public image?: File) {
  }
}
