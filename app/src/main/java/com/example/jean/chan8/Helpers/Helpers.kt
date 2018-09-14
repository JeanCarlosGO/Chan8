package com.example.jean.chan8.Helpers

import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.EditText

fun showMessage(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show()
}

fun validateEmailAndPassword(userEmail: EditText, password: EditText): Boolean {
    var valid = true

    val email = userEmail.text.toString()
    val pw = password.text.toString()

    // Validate Email
    if (TextUtils.isEmpty(email)) {
        userEmail.error = "Required"
        valid = false
    } else {
        userEmail.error = null
    }

    // Validate Password
    if (TextUtils.isEmpty(pw)) {
        password.error = "Required"
        valid = false
    } else {
        password.error = null
    }

    return valid
}