
from rest_framework import viewsets
from cliente.api import serializers
from cliente import models

class ClienteViewSet(viewsets.ModelViewSet):
    serializer_class = serializers.ClienteSerializer
    queryset = models.Clientes.objects.all()

class DepositoViewSet(viewsets.ModelViewSet):
    serializer_class = serializers.DepositoSerializer
    queryset = models.Deposito.objects.all()
    

