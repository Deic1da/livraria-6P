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
  styleUrl: './login.css',
})
export class Login {
  private auth = inject(AuthService);
  private router = inject(Router);

  email = '';
  senha = '';
  erro = signal('');
  // a mesma tela serve pra entrar e pra criar conta
  criando = signal(false);

  enviar() {
    const credenciais = { email: this.email, senha: this.senha };
    // criar conta = registrar e já entrar; entrar = só o login
    const pedido = this.criando()
      ? this.auth.registrar(credenciais).pipe(switchMap(() => this.auth.login(credenciais)))
      : this.auth.login(credenciais);

    pedido.subscribe({
      next: () => this.router.navigate(['/livros']),
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }

  alternar() {
    this.criando.update((v) => !v);
    this.erro.set('');
  }
}

// a API devolve o ErrorResponseDto com o campo "message"; sem resposta, a API está fora do ar
export function mensagemDeErro(e: HttpErrorResponse) {
  if (e.status === 0) return 'Não foi possível falar com a API. Confira se ela está rodando na porta 8080.';
  return e.error?.message ?? 'Erro inesperado';
}
