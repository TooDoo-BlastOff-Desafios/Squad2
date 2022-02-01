package br.com.toodoo.fipay.ui.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import br.com.toodoo.fipay.R
import br.com.toodoo.fipay.helper.FirebaseHelper
import br.com.toodoo.fipay.model.User
import br.com.toodoo.fipay.ui.HomeActivity
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {

    private lateinit var signInLayout: View
    private lateinit var editEmail: TextView
    private lateinit var editPassword: TextView
    private lateinit var cbRememberMe: CheckBox
    private lateinit var progressBar: ProgressBar

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initComponents()

        setClickListeners()
    }

    private fun initComponents() {
        signInLayout = findViewById(R.id.siginLayout)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        cbRememberMe = findViewById(R.id.cbRememberMe)
        progressBar = findViewById(R.id.progressBar)

        sharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun setClickListeners() {
        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            progressBar.visibility = View.VISIBLE
            validateFields()
        }

        findViewById<TextView>(R.id.txtForgotPassword).setOnClickListener {
            startActivity(Intent(this@SignInActivity, ForgotPasswordActivity::class.java))
        }

        findViewById<TextView>(R.id.textSignUp).setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }


        cbRememberMe.setOnClickListener {
            editor.putBoolean(getString(R.string.shp_remember_me), cbRememberMe.isChecked)
            editor.apply()
        }
    }

    private fun validateFields() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                val user = User()
                user.email = email
                user.password = password

                signIn(user)
            } else {
                editPassword.error = "You must type a password"
            }
        } else {
            editEmail.error = "You must type an e-mail"
            editEmail.requestFocus()

        }
    }

    private fun signIn(user: User) {
        FirebaseHelper.getFirebaseAuth().signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (emailIsVerified()) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(signInLayout, "Please verify your e-mail address.", Snackbar.LENGTH_LONG).show()
                        FirebaseHelper.getFirebaseAuth().signOut()
                        progressBar.visibility = View.GONE
                    }
                } else {
                    it.exception?.let { exception -> Snackbar.make(signInLayout, exception.message!!, Snackbar.LENGTH_LONG).show() }
                    progressBar.visibility = View.GONE
                }
            }
    }

    private fun emailIsVerified(): Boolean {
        FirebaseHelper.getFirebaseAuth().currentUser?.let {
            return it.isEmailVerified
        }

        return false
    }
}