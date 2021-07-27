package com.emanuelgalvao.pantry.util

import android.app.AlertDialog
import android.content.Context
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

        fun showInfoDialog(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { _, _ -> run {} }

            builder.show()
        }
    }
}