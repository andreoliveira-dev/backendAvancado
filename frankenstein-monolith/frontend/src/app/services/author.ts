import { Injectable } from '@angular/core';

import { HttpClient }
from '@angular/common/http';

import { Observable }
from 'rxjs';

export interface Author {

  id?: number;

  name: string;

  cpf: string;

  annualIncome: number;

  totalBooks?: number;

  createdAt?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  // DJANGO
  private apiUrl =
    'http://localhost:8000/api/v1/authors';

  constructor(
    private http: HttpClient
  ) {}

  // LISTAR
  getAuthors(): Observable<Author[]> {

    return this.http.get<Author[]>(
      this.apiUrl
    );
  }

  // CRIAR
  saveAuthor(
    author: Author
  ): Observable<Author> {

    return this.http.post<Author>(
      this.apiUrl,
      author
    );
  }

  // DELETE
  deleteAuthor(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }
}