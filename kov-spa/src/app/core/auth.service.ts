import {Injectable} from '@angular/core';


@Injectable()
export class AuthService {


  authenticateUser(token) {
    window.localStorage.setItem('token', token);
  }

  saveUser(user) {
    window.localStorage.setItem('user', user);
  }

  addRoles(roles) {
    window.localStorage.setItem('roles', roles);
  }

  getUser() {
    return window.localStorage.getItem('user');
  }

  isUserAuthenticated() {
    return window.localStorage.getItem('token') !== null;
  }

  deauthenticateUser() {
    window.localStorage.removeItem('token');
  }

  getToken() {
    return window.localStorage.getItem('token');
  }

  removeUser() {
    window.localStorage.removeItem('user');
  }

  removeRoles() {
    window.localStorage.removeItem('roles');
  }
}
