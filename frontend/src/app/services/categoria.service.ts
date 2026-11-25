import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Categoria } from '../models/categoria';

// Usado no cadastro de livro: a lista de escolha (GET) e o "+ novo" (POST)
@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/api/v1/categorias`;

  listar() {
    return this.http.get<Categoria[]>(this.url);
  }

  // cadastra só com o nome; os outros campos são opcionais na API
  cadastrar(nome: string) {
    return this.http.post<Categoria>(this.url, { nome });
  }
}
