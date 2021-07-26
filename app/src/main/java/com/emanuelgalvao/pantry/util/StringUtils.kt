package com.emanuelgalvao.pantry.util

import android.text.TextUtils

class StringUtils {

    companion object {

        fun validateEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validatePassword(password: String): Boolean {
            return password.length >= 6
        }
    }
}