from django.db import models


class Author(models.Model):
    name = models.CharField(max_length=255)
    cpf = models.CharField(max_length=11)
    annual_income = models.FloatField()

    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.name