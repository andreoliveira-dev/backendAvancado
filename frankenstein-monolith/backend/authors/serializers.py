from rest_framework import serializers

from .models import Author


class AuthorSerializer(serializers.ModelSerializer):

    annualIncome = serializers.FloatField(
        source='annual_income'
    )

    createdAt = serializers.DateTimeField(
        source='created_at',
        read_only=True
    )

    totalBooks = serializers.SerializerMethodField()

    class Meta:
        model = Author
        fields = [
            'id',
            'name',
            'cpf',
            'annualIncome',
            'createdAt',
            'totalBooks'
        ]

    def get_totalBooks(self, obj):
        return obj.books.count()