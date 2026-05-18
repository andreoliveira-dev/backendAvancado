from rest_framework import serializers

from authors.serializers import AuthorSerializer

from .models import Book


class BookSerializer(serializers.ModelSerializer):

    launchDate = serializers.DateField(
        source='launch_date'
    )

    author = AuthorSerializer(read_only=True)

    author_id = serializers.IntegerField(write_only=True)

    class Meta:
        model = Book
        fields = [
            'id',
            'title',
            'price',
            'launchDate',
            'author',
            'author_id'
        ]

    def create(self, validated_data):

        author_id = validated_data.pop('author_id')

        validated_data['author_id'] = author_id

        return Book.objects.create(**validated_data)