import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

// Fica no meio de toda requisição do HttpClient: é o "outro lado" do JwtFilter da API
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  // coloca o token em toda requisição (no login e no cadastro ainda não há token)
  const token = auth.token();
  if (token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }

  // se a API responder 401 (token vencido ou inválido), apaga o token e volta pro login
  return next(req).pipe(
    catchError((erro: HttpErrorResponse) => {
      if (erro.status === 401 && !req.url.includes('/api/auth/')) {
        auth.sair();
        router.navigate(['/login']);
      }
      return throwError(() => erro);
    }),
  );
};
