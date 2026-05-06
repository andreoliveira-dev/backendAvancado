import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <div style="background: white; color: black; padding: 100px; text-align: center; height: 100vh;">
      <h1>ANGULAR IS WORKING!</h1>
      <p>Se você está vendo isso, o framework carregou.</p>
    </div>
  `
})
export class AppComponent {
  title = 'frontend';
}
