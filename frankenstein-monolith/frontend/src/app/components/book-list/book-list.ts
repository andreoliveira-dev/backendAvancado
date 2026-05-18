import {
  Component,
  OnInit
} from '@angular/core';

import { CommonModule }
from '@angular/common';

import {
  BookService,
  Book
} from '../../services/book';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './book-list.html',
  styleUrl: './book-list.css'
})
export class BookListComponent
implements OnInit {

  books: Book[] = [];

  loading = true;

  errorMessage = '';

  constructor(
    private bookService: BookService
  ) {}

  ngOnInit(): void {

    this.loadBooks();
  }

  loadBooks(): void {

    this.loading = true;

    this.bookService.getBooks()
      .subscribe({

        next: (data) => {

          this.books = data;

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

  deleteBook(
    id: number | undefined
  ): void {

    if (!id) return;

    this.bookService.deleteBook(id)
      .subscribe({

        next: () => {

          // AUTO REFRESH
          this.loadBooks();
        },

        error: (err) => {

          console.error(err);
        }
      });
  }
}