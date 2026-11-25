// Espelha o LivroResponseDto da API (o que chega no GET)
export interface Livro {
  id: string;
  titulo: string;
  autor: string; // nome do autor
  categoria: string; // nome da categoria
  editora: string; // nome da editora
  isbn: string;
  anoPublicacao: number;
  preco: number;
}

// Espelha o LivroRequestDto da API (o que o front manda no POST e no PUT)
export interface LivroRequest {
  titulo: string;
  autor: string; // id do autor
  categoria: string; // id da categoria
  editora: string; // id da editora
  isbn: string;
  anoPublicacao: number;
  preco: number;
}
