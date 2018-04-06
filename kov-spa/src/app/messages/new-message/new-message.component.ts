import {Component, OnInit} from '@angular/core';
import {NewMessageModel} from './new.message.model';
import {AppConstants} from '../../app-constants';
import {Toolbox} from '../../utils/toolbox';

@Component({
  selector: 'new-message',
  templateUrl: './new-message.component.html',
  styleUrls: ['../../app.component.scss']
})
export class NewMessageComponent {

  readonly RECEIVER_MIN_LENGTH: number = AppConstants.HERO_NAME_MIN_LENGTH;
  readonly RECEIVER_MAX_LENGTH: number = AppConstants.HERO_NAME_MAX_LENGTH;
  readonly TITLE_MIN_LENGTH: number = 5;
  readonly TITLE_MAX_LENGTH: number = 35;
  readonly CONTENT_MIN_LENGTH: number = 5;
  readonly CONTENT_MAX_LENGTH: number = 5000;


  message: NewMessageModel = new NewMessageModel();

  onSubmit() {
    console.log(this.message);
  }

  isReceiverValid(receiver: string): boolean {
    return Toolbox.isContentLengthBetween(receiver,
      this.RECEIVER_MIN_LENGTH, this.RECEIVER_MAX_LENGTH);
  }

  isTitleValid(title: string): boolean {
    return Toolbox.isContentLengthBetween(title,
      this.TITLE_MIN_LENGTH, this.TITLE_MAX_LENGTH);
  }

  isContentValid(content: string): boolean {
    return Toolbox.isContentLengthBetween(content,
      this.CONTENT_MIN_LENGTH, this.CONTENT_MAX_LENGTH);
  }

}
