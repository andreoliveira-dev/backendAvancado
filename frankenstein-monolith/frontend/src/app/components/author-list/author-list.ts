import { Component, OnInit } from '@angular/core';
import { AuthorService, Author } from '../../services/author';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-author-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div style="padding: 50px; text-align: center;">
      <h2 style="color: #818cf8;">Author Management Panel</h2>
      <div *ngIf="loading" style="color: #94a3b8;">
        <p>🔄 Aguardando conexão com o backend (Incompatibilidade Proposital)...</p>
      </div>
      <div *ngIf="errorMessage" style="background: rgba(239, 68, 68, 0.1); padding: 20px; border-radius: 10px; color: #fca5a5;">
        {{ errorMessage }}
      </div>
    </div>
  `
})
export class AuthorListComponent implements OnInit {
  authors: Author[] = [];
  loading = true;
  errorMessage = '';

  constructor(private authorService: AuthorService) {}

  ngOnInit(): void {
    this.loadAuthors();
  }

  loadAuthors(): void {
    this.authorService.getAuthors().subscribe({
      next: (data) => {
        this.authors = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load authors. The backend might be incompatible.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deleteAuthor(id: number | undefined): void {
    if (id) {
      this.authorService.deleteAuthor(id).subscribe(() => this.loadAuthors());
    }
  }
}
