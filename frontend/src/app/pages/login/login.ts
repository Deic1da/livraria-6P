import { Component, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { AuthService } from '../../services/auth.service';

// Tela de login e cadastro de usuário: os dois são POST na API
@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
})
export class Login {
  private auth = inject(AuthService);
  private router = inject(Router);

  email = '';
  senha = '';
  erro = signal('');

  entrar() {
    this.auth.login({ email: this.email, senha: this.senha }).subscribe({
      next: () => this.router.navigate(['/livros']),
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }

  // cria a conta e já entra com ela
  criarConta() {
    const credenciais = { email: this.email, senha: this.senha };
    this.auth
      .registrar(credenciais)
      .pipe(switchMap(() => this.auth.login(credenciais)))
      .subscribe({
        next: () => this.router.navigate(['/livros']),
        error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
      });
  }
}

// a API devolve o ErrorResponseDto com o campo "message"; sem resposta, a API está fora do ar
export function mensagemDeErro(e: HttpErrorResponse) {
  if (e.status === 0) return 'Não foi possível falar com a API. Ela está rodando?';
  return e.error?.message ?? 'Erro inesperado';
}
