from rest_framework import status
from rest_framework import viewsets

from rest_framework.permissions import IsAdminUser
from rest_framework.response import Response

from .models import Author
from .serializers import AuthorSerializer
from .services import AuthorService


class AuthorViewSet(viewsets.ModelViewSet):

    queryset = Author.objects.prefetch_related(
        'books'
    ).all()

    serializer_class = AuthorSerializer

    def get_permissions(self):

        if self.action == 'destroy':
            return [IsAdminUser()]

        return []

    def create(self, request, *args, **kwargs):

        serializer = self.get_serializer(
            data=request.data
        )

        serializer.is_valid(
            raise_exception=True
        )

        try:

            author = AuthorService.create_author(
                {
                    'name': serializer.validated_data['name'],
                    'cpf': serializer.validated_data['cpf'],
                    'annual_income': serializer.validated_data['annual_income']
                }
            )

        except ValueError as e:

            return Response(
                {'error': str(e)},
                status=status.HTTP_400_BAD_REQUEST
            )

        response_serializer = self.get_serializer(author)

        return Response(
            response_serializer.data,
            status=status.HTTP_201_CREATED
        )