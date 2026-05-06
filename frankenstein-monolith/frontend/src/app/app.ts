import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <main>
      <h1 style="text-align: center; color: white;">DevOps Lab: Hexagonal Refactoring</h1>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    main {
      padding-top: 2rem;
    }
  `]
})
export class AppComponent {
  title = 'frontend';
}
