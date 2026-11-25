import { Component, inject, OnInit, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { Livro } from '../../models/livro';
import { AuthService } from '../../services/auth.service';
import { LivroService } from '../../services/livro.service';
import { mensagemDeErro } from '../login/login';

// Lista os livros (GET /api/v1/livros) assim que a tela abre
@Component({
  selector: 'app-livros',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './livros.html',
})
export class Livros implements OnInit {
  private livroService = inject(LivroService);
  private auth = inject(AuthService);
  private router = inject(Router);

  // signal: quando a lista muda, a tela se atualiza sozinha
  livros = signal<Livro[]>([]);
  erro = signal('');

  ngOnInit() {
    this.livroService.listar().subscribe({
      next: (lista) => this.livros.set(lista),
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }

  sair() {
    this.auth.sair();
    this.router.navigate(['/login']);
  }
}
