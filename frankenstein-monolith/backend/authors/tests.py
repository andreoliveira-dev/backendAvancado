from django.test import TestCase

from .models import Author


class AuthorTest(TestCase):

    def test_create_author(self):

        author = Author.objects.create(
            name='Zara',
            cpf='12345678901',
            annual_income=50000
        )

        self.assertEqual(
            author.name,
            'Zara'
        )

    def test_invalid_cpf(self):

        with self.assertRaises(Exception):

            Author.objects.create(
                name='Erro',
                cpf='123',
                annual_income=1000
            )