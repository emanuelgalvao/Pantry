package com.emanuelgalvao.pantry.util

import android.view.View
import com.emanuelgalvao.pantry.R
import com.google.android.material.snackbar.Snackbar


class AlertUtils {

    companion object {
        fun showSnackbar(view: View, message: String) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(view.resources.getColor(R.color.background_red))
            snackbar.show()
        }
    }
}