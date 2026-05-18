import {
  Component,
  OnInit
} from '@angular/core';

import { CommonModule }
from '@angular/common';

import {
  AuthorService,
  Author
} from '../../services/author';

@Component({
  selector: 'app-author-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './author-list.html',
  styleUrls: ['./author-list.css']
})
export class AuthorListComponent
implements OnInit {

  authors: Author[] = [];

  loading = true;

  errorMessage = '';

  constructor(
    private authorService: AuthorService
  ) {}

  ngOnInit(): void {

    this.loadAuthors();
  }

  loadAuthors(): void {

    this.loading = true;

    this.authorService.getAuthors()
      .subscribe({

        next: (data) => {

          this.authors = data;

          this.loading = false;

          this.errorMessage = '';
        },

        error: (err) => {

          this.loading = false;

          this.errorMessage =
            'Could not connect to Django backend API';

          console.error(err);
        }
      });
  }

  deleteAuthor(
    id: number | undefined
  ): void {

    if (!id) return;

    this.authorService.deleteAuthor(id)
      .subscribe({

        next: () => {

          // AUTO REFRESH
          this.loadAuthors();
        },

        error: (err) => {

          console.error(err);
        }
      });
  }
}