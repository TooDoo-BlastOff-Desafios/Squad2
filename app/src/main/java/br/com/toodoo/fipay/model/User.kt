package br.com.toodoo.fipay.model

import com.google.firebase.database.Exclude
import java.io.Serializable

class User(
    var id: String = "",
    var cpf: String = "",
    var email: String = "",
    @get:Exclude var password: String = "",
    var address: Address = Address()
) : Serializable {
}