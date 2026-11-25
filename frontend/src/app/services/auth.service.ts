import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Credenciais, TokenResponse } from '../models/auth';

const CHAVE_TOKEN = 'token';

// Cadastro, login e o token: guardado no localStorage pra sobreviver a um recarregamento da página
@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/api/auth`;

  registrar(credenciais: Credenciais) {
    return this.http.post<void>(`${this.url}/register`, credenciais);
  }

  // depois do login, guarda o token que a API devolveu
  login(credenciais: Credenciais) {
    return this.http
      .post<TokenResponse>(`${this.url}/login`, credenciais)
      .pipe(tap((resposta) => localStorage.setItem(CHAVE_TOKEN, resposta.token)));
  }

  sair() {
    localStorage.removeItem(CHAVE_TOKEN);
  }

  token() {
    return localStorage.getItem(CHAVE_TOKEN);
  }

  estaLogado() {
    return this.token() !== null;
  }
}
