from rest_framework import serializers
from cliente import models

class ClienteSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = models.Clientes
        fields = '__all__'

class DepositoSerializer(serializers.ModelSerializer):

    class Meta:
        model = models.Deposito
        fields = '__all__'

    

    


