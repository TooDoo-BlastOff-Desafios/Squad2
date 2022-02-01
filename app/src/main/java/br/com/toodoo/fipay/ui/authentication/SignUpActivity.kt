package br.com.toodoo.fipay.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.toodoo.fipay.R
import br.com.toodoo.fipay.helper.FirebaseHelper
import br.com.toodoo.fipay.model.Address
import br.com.toodoo.fipay.model.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editCPF: EditText
    private lateinit var editCountry: EditText
    private lateinit var editState: EditText
    private lateinit var editCity: EditText
    private lateinit var editStreet: EditText
    private lateinit var btnCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initComponents()

        btnCreateAccount.setOnClickListener {
            validateFields()
        }
    }

    private fun initComponents() {
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        editCPF = findViewById(R.id.editCPF)
        editCountry = findViewById(R.id.editCountry)
        editState = findViewById(R.id.editState)
        editCity = findViewById(R.id.editCity)
        editStreet = findViewById(R.id.editStreet)
    }

    private fun sendEmailVerification() {
        FirebaseHelper.getFirebaseAuth().currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                run {
                    if (task.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "Enviado", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun saveUserData(user: User) {
        val userRef = FirebaseHelper.getDbReference()
            .child("users")
            .child(user.id.toString())

        userRef.setValue(user).addOnCompleteListener { task ->
            run {
                if (task.isSuccessful) {
                    finish()
                    startActivity(Intent(this, SignInActivity::class.java))
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun registerUser(user: User) {
        FirebaseHelper.getFirebaseAuth()
            .createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.let {
                        val id = it.uid
                        user.id = id

                        sendEmailVerification()
                        saveUserData(user)
                    }
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }

            }
    }

    private fun validateFields() {
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()
        val cpf = editCPF.text.toString().trim()
        val country = editCountry.text.toString().trim()
        val state = editState.text.toString().trim()
        val city = editCity.text.toString().trim()
        val street = editStreet.text.toString().trim()

        if (email.isEmpty()) {
            editEmail.error = "You must type an e-mail."
            editEmail.requestFocus()

            return
        }

        if (password.isEmpty()) {
            editPassword.error = "You must type a password."
            editPassword.requestFocus()

            return
        }

        if (cpf.isEmpty()) {
            editCPF.error = "You must type an CPF."
            editCPF.requestFocus()

            return
        }

        if (country.isEmpty()) {
            editCountry.error = "You must type a Country."
            editCountry.requestFocus()

            return
        }

        if (state.isEmpty()) {
            editState.error = "You must type a State."
            editState.requestFocus()

            return
        }

        if (city.isEmpty()) {
            editCity.error = "You must type a City."
            editCity.requestFocus()

            return
        }

        if (street.isEmpty()) {
            editStreet.error = "You must type a Street."
            editStreet.requestFocus()

            return
        }

        val address = Address(country, state, city, street)
        val user = User("", cpf, email, password, address)

        registerUser(user)
    }

}