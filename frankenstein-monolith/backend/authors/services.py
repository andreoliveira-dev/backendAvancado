from .models import Author


class AuthorService:

    @staticmethod
    def create_author(validated_data):

        cpf = validated_data.get('cpf')

        if len(cpf) != 11:
            raise ValueError('CPF inválido')

        return Author.objects.create(**validated_data)

    @staticmethod
    def delete_author(author):
        author.delete()