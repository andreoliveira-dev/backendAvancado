from django.contrib import admin

from django.urls import path
from django.urls import include

from rest_framework.routers import DefaultRouter

from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

from authors.views import AuthorViewSet
from books.views import BookViewSet

router = DefaultRouter(
    trailing_slash=False
)

router.register(
    r'api/v1/authors',
    AuthorViewSet
)

router.register(
    r'api/v1/books',
    BookViewSet
)

urlpatterns = [

    path(
        'admin/',
        admin.site.urls
    ),

    path(
        'api/token',
        TokenObtainPairView.as_view()
    ),

    path(
        'api/token/refresh',
        TokenRefreshView.as_view()
    ),

    path(
        '',
        include(router.urls)
    ),
]