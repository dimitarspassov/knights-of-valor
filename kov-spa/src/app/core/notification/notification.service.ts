import {EventEmitter, Injectable} from '@angular/core';

@Injectable()
export class NotificationService {

  private message: string;
  messageUpdated: EventEmitter<String> = new EventEmitter();
  errorUpdated: EventEmitter<String> = new EventEmitter();

  notify(message: string) {
    this.message = message;
    this.messageUpdated.emit(message);
  }

  showError(message: string) {
    this.message = message;
    this.errorUpdated.emit(message);
  }
}
