from .models import Book


class BookService:

    @staticmethod
    def create_book(validated_data):
        return Book.objects.create(**validated_data)