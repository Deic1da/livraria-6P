import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

// Component raiz: só a "moldura" com o <router-outlet>, onde cada tela aparece
@Component({
  imports: [RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {}
