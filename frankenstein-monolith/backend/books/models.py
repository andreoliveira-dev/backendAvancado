from django.db import models

from authors.models import Author


class Book(models.Model):
    title = models.CharField(max_length=255)

    price = models.FloatField()

    launch_date = models.DateField()

    author = models.ForeignKey(
        Author,
        on_delete=models.CASCADE,
        related_name='books'
    )

    def __str__(self):
        return self.title