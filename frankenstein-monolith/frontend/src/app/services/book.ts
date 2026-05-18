import { Injectable } from '@angular/core';

import { HttpClient }
from '@angular/common/http';

import { Observable }
from 'rxjs';

import { Author }
from './author';

export interface Book {

  id?: number;

  title: string;

  price?: number;

  launchDate?: string;

  author: Author;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl =
    'http://localhost:8000/api/v1/books';

  constructor(
    private http: HttpClient
  ) {}

  // LISTAR
  getBooks(): Observable<Book[]> {

    return this.http.get<Book[]>(
      this.apiUrl
    );
  }

  // CRIAR
  saveBook(
    book: Book
  ): Observable<Book> {

    return this.http.post<Book>(
      this.apiUrl,
      book
    );
  }

  // DELETE
  deleteBook(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }
}