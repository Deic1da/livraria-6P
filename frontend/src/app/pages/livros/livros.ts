import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { Livro } from '../../models/livro';
import { AuthService } from '../../services/auth.service';
import { LivroService } from '../../services/livro.service';
import { mensagemDeErro } from '../login/login';

// tons suaves pro pontinho de cada categoria
const TONS = ['#7c8cc9', '#6faf9a', '#d19a66', '#b58ac4', '#6aa7c4', '#c98a8a'];

// Lista de livros (GET /api/v1/livros) assim que a tela abre, com busca por título ou autor
@Component({
  selector: 'app-livros',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './livros.html',
  styleUrl: './livros.css',
})
export class Livros implements OnInit {
  private livroService = inject(LivroService);
  private auth = inject(AuthService);
  private router = inject(Router);

  // signal: quando a lista muda, a tela se atualiza sozinha
  livros = signal<Livro[]>([]);
  carregado = signal(false);
  erro = signal('');
  busca = signal('');
  // mensagem vinda da tela de cadastro ("Livro cadastrado")
  aviso = signal<string>(history.state?.aviso ?? '');

  // a busca filtra na hora, sem chamar a API de novo
  filtrados = computed(() => {
    const termo = this.busca().trim().toLowerCase();
    if (!termo) return this.livros();
    return this.livros().filter((l) => `${l.titulo} ${l.autor}`.toLowerCase().includes(termo));
  });

  // cada categoria ganha um tom, na ordem em que aparece
  private tons = computed(() => {
    const categorias = [...new Set(this.livros().map((l) => l.categoria))];
    return new Map(categorias.map((c, i) => [c, TONS[i % TONS.length]]));
  });

  ngOnInit() {
    this.livroService.listar().subscribe({
      next: (lista) => {
        this.livros.set(lista);
        this.carregado.set(true);
      },
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
    if (this.aviso()) setTimeout(() => this.aviso.set(''), 4000);
  }

  tom(categoria: string) {
    return this.tons().get(categoria);
  }

  sair() {
    this.auth.sair();
    this.router.navigate(['/login']);
  }
}
