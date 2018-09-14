package com.example.jean.chan8

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.jean.chan8.Helpers.showMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


abstract class AuthAbstractActivity : BaseActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()

        val user = mAuth.currentUser
        updateUI(user)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                updateUI(null)
            }
        }
    }

    fun signInWithEmailAndPassword(view: View, email: String, password: String) {
        hideKeyboard(view)
        Log.d(TAG, "signIn:$email")

        showProgressDialog("Authenticating...")

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                updateUI(mAuth.currentUser)
            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)

                showMessage(view, "Error: ${task.exception?.message}")
                updateUI(null)
            }
        }
    }

    fun signInWithGoogle(view: View) {
        hideKeyboard(view)
        Log.d(TAG, "signIn")

        showProgressDialog("Authenticating...")

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    fun register(view: View, email: String, password: String) {
        hideKeyboard(view)
        Log.d(TAG, "register:$email")

        showProgressDialog("Registering...")

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                updateUI(mAuth.currentUser)
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)

                showMessage(view, "Error: ${task.exception?.message}")
                updateUI(null)
            }
        }
    }

    fun signOut(view: View) {
        hideKeyboard(view)
        Log.d(TAG, "signOut")
        showProgressDialog("Signing Out...")

        // Firebase Sign Out
        mAuth.signOut()

        // Google Sign Out
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                updateUI(null)
            }
            //mGoogleSignInClient.revokeAccess()
            return
        }
        updateUI(null)
    }

    //Authenticate Firebase with Google account
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        showProgressDialog("Authenticating...")

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser
                        updateUI(user)

                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }

                    hideProgressDialog()
                }
    }

    open fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()
    }

    companion object {
        protected val TAG = "Auth"
        private val RC_SIGN_IN = 9001
    }
}
