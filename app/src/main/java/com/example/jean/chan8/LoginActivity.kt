package com.example.jean.chan8

import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*
import android.os.Bundle
import android.view.View
import com.example.jean.chan8.Helpers.validateEmailAndPassword
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AuthAbstractActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Email and Password Buttons
        btnSignIn.setOnClickListener(this)
        btnRegister.setOnClickListener(this)

        //Google Buttons
        btnSignInGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRegister -> {
                val email = user.text.toString()
                val pw = pass.text.toString()
                if (!validateEmailAndPassword(user, pass)) {
                    return
                }
                register(v, email, pw)
            }

            R.id.btnSignIn -> {
                val email = user.text.toString()
                val pw = pass.text.toString()
                if (!validateEmailAndPassword(user, pass)) {
                    return
                }
                signInWithEmailAndPassword(v, email, pw)
            }

            R.id.btnSignInGoogle -> signInWithGoogle(v)
        }
    }

/*
    fun signIn(view: View) {
        when(view.id) {
            R.id.btnSignIn -> signInWithEmailAndPassword(view, user.text.toString(), pass.text.toString())
            R.id.btnSignInGoogle -> signInWithGoogle(view)
        }
    }
*/

    override fun updateUI(user: FirebaseUser?) {
        super.updateUI(user)

        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            status.text = "Signed Out"
        }
    }

    companion object {
        protected val TAG = "Login"
    }
}
