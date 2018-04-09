import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions} from '@angular/http';
import {AuthService} from './auth.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';


@Injectable()
export class HttpService {

  private readonly BASE_URL = 'http://localhost:8000/';
  private readonly GET_METHOD = 'get';
  private readonly POST_METHOD = 'post';

  constructor(private http: Http,
              private authService: AuthService) {
  }

  get(url, authenticated = false) {
    const requestOptions = this.getRequestOptions(this.GET_METHOD, authenticated, false);

    return this.http
      .get(this.BASE_URL + url, requestOptions)
      .map(res => res.json());
  }

  post(url, data, authenticated = false, multipart = false) {
    const requestOptions = this.getRequestOptions(this.POST_METHOD, authenticated, multipart);

    if (multipart) {
      return this.http
        .post(this.BASE_URL + url, data, requestOptions)
        .map(res => res.json())
        .catch((e: Response) => Observable.throw(e));
    } else {
      return this.http
        .post(this.BASE_URL + url, JSON.stringify(data), requestOptions)
        .map(res => res.json())
        .catch((e: Response) => Observable.throw(e));
    }


  }

  private getRequestOptions(method, authenticated, multipart) {

    const headers = new Headers();

    if (multipart) {
      headers.append('enctype', 'multipart/form-data');
    } else if (method !== this.GET_METHOD) {
      headers.append('Content-Type', 'application/json');
    }

    if (authenticated) {
      headers.append('Authorization', `Bearer ${this.authService.getToken()}`);
    }

    const requestOptions = new RequestOptions({
      headers: headers
    });

    return requestOptions;
  }
}
