import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Livro, LivroRequest } from '../models/livro';

// Todas as chamadas da API de livros ficam aqui; as telas só pedem pro service
@Injectable({ providedIn: 'root' })
export class LivroService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/api/v1/livros`;

  listar() {
    return this.http.get<Livro[]>(this.url);
  }

  cadastrar(livro: LivroRequest) {
    return this.http.post<Livro>(this.url, livro);
  }
}
