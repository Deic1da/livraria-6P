import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

// Porteiro das telas: roda antes da tela abrir. Só confere se tem token guardado;
// quem garante a segurança de verdade é a API (o JwtFilter confere o token)
export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  return auth.estaLogado() ? true : inject(Router).createUrlTree(['/login']);
};
