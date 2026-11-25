import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Autor } from '../models/autor';

// Usado no cadastro de livro: a lista de escolha (GET) e o "+ novo" (POST)
@Injectable({ providedIn: 'root' })
export class AutorService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/api/v1/autores`;

  listar() {
    return this.http.get<Autor[]>(this.url);
  }

  // cadastra só com o nome; os outros campos são opcionais na API
  cadastrar(nome: string) {
    return this.http.post<Autor>(this.url, { nome });
  }
}
