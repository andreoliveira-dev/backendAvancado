import os
import django
import random

from faker import Faker

# CONFIG DJANGO
os.environ.setdefault(
    'DJANGO_SETTINGS_MODULE',
    'config.settings'
)

django.setup()

# IMPORT MODELS
from authors.models import Author
from books.models import Book

fake = Faker('pt_BR')

# LIMPA BANCO
Book.objects.all().delete()
Author.objects.all().delete()

authors = []

print('Criando autores...')

# CRIA AUTORES
for _ in range(20):

    author = Author.objects.create(

        name=fake.name(),

        cpf=''.join(
            [str(random.randint(0, 9))
             for _ in range(11)]
        ),

        annual_income=random.randint(
            30000,
            500000
        )
    )

    authors.append(author)

print('Criando livros...')

# CRIA LIVROS
for _ in range(120):

    Book.objects.create(

        title=fake.sentence(
            nb_words=4
        ),

        price=round(
            random.uniform(20, 300),
            2
        ),

        launch_date=fake.date_between(
            start_date='-10y',
            end_date='today'
        ),

        author=random.choice(authors)
    )

print('Banco populado com sucesso.')