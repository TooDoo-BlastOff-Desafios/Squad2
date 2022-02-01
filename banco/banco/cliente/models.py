from django.db import models
from django.forms import DateField, IntegerField
from uuid import uuid4

# Create your models here.


class Clientes(models.Model):
    nome = models.CharField(max_length=255)
    email = models.CharField(max_length=255)
    password = models.CharField(max_length=50)
    country = models.CharField(max_length=255)
    state = models.CharField(max_length=255)
    city = models.CharField(max_length=255)
    street = models.CharField(max_length=255)
    cpf = models.CharField(primary_key=True, max_length=11, unique=True)


class Deposito(models.Model):    
    description = models.CharField(max_length=140)
    cash_value = models.FloatField()
    date = models.DateField(max_length=8)
    cliente = models.ForeignKey(Clientes, on_delete=models.CASCADE)
