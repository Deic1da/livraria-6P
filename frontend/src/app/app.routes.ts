import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { Login } from './pages/login/login';
import { LivroForm } from './pages/livro-form/livro-form';
import { Livros } from './pages/livros/livros';

// Endereço de cada tela do front (as rotas da API ficam nos services).
// As telas de livros só abrem com login (authGuard); o /login é livre.
export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'livros', component: Livros, canActivate: [authGuard] },
  { path: 'livros/novo', component: LivroForm, canActivate: [authGuard] },
  { path: '', redirectTo: 'livros', pathMatch: 'full' },
  { path: '**', redirectTo: 'livros' },
];
