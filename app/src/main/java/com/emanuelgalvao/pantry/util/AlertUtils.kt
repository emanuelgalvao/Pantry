package com.emanuelgalvao.pantry.util

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar


class AlertUtils {

    companion object {
        fun showSnackbar(view: View, message: String, color: Int) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            val snackView = snackbar.view
            val params = snackView.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackView.layoutParams = params
            snackbar.setBackgroundTint(color)
            snackbar.show()
        }
    }
}