import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { LivroRequest } from '../../models/livro';
import { AutorService } from '../../services/autor.service';
import { CategoriaService } from '../../services/categoria.service';
import { EditoraService } from '../../services/editora.service';
import { LivroService } from '../../services/livro.service';
import { mensagemDeErro } from '../login/login';

type Tipo = 'autor' | 'categoria' | 'editora';
interface Opcao {
  id: string;
  nome: string;
}

// Cadastro de livro: 3 GETs pras listas de escolha, POST do livro e POST do "+ novo"
@Component({
  selector: 'app-livro-form',
  imports: [FormsModule, RouterLink],
  templateUrl: './livro-form.html',
})
export class LivroForm implements OnInit {
  private livroService = inject(LivroService);
  private autorService = inject(AutorService);
  private categoriaService = inject(CategoriaService);
  private editoraService = inject(EditoraService);
  private router = inject(Router);

  // as opções de cada lista vêm da API
  opcoes = {
    autor: signal<Opcao[]>([]),
    categoria: signal<Opcao[]>([]),
    editora: signal<Opcao[]>([]),
  };

  livro: LivroRequest = {
    titulo: '',
    autor: '',
    categoria: '',
    editora: '',
    isbn: '',
    anoPublicacao: new Date().getFullYear(),
    preco: 0,
  };

  // as três listas de escolha, na ordem em que aparecem na tela
  tipos: { chave: Tipo; rotulo: string }[] = [
    { chave: 'autor', rotulo: 'Autor' },
    { chave: 'categoria', rotulo: 'Categoria' },
    { chave: 'editora', rotulo: 'Editora' },
  ];

  // "+ novo": qual lista está ganhando um item novo e o nome digitado
  criando = signal<Tipo | null>(null);
  novoNome = '';
  erro = signal('');

  ngOnInit() {
    this.carregar('autor', this.autorService.listar());
    this.carregar('categoria', this.categoriaService.listar());
    this.carregar('editora', this.editoraService.listar());
  }

  salvar() {
    this.livroService.cadastrar(this.livro).subscribe({
      next: () => this.router.navigate(['/livros']),
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }

  abrirNovo(tipo: Tipo) {
    this.criando.set(tipo);
    this.novoNome = '';
  }

  // cadastra o autor/categoria/editora na API e já deixa ele escolhido na lista
  adicionarNovo() {
    const tipo = this.criando();
    if (!tipo || !this.novoNome.trim()) return;
    const cadastrar: Record<Tipo, (nome: string) => Observable<Opcao>> = {
      autor: (nome) => this.autorService.cadastrar(nome),
      categoria: (nome) => this.categoriaService.cadastrar(nome),
      editora: (nome) => this.editoraService.cadastrar(nome),
    };
    cadastrar[tipo](this.novoNome.trim()).subscribe({
      next: (item) => {
        this.opcoes[tipo].update((lista) => [...lista, item]);
        this.livro[tipo] = item.id;
        this.criando.set(null);
      },
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }

  private carregar(tipo: Tipo, requisicao: Observable<Opcao[]>) {
    requisicao.subscribe({
      next: (lista) => this.opcoes[tipo].set(lista),
      error: (e: HttpErrorResponse) => this.erro.set(mensagemDeErro(e)),
    });
  }
}
