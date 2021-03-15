package com.roomedia.dakku.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.AlertInputPasswordBinding

private fun showPasswordDialog(context: Context, okCallback: () -> Unit, @StringRes titleId: Int): AlertDialog {
    val binding = AlertInputPasswordBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).apply {
        titleTextView.text = titleTextView.text.toString()
            .format(context.getString(titleId))
    }

    val dialog = AlertDialog
        .Builder(context)
        .setView(binding.root)
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(android.R.string.ok, null)
        .show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        // MARK: temporal password checking
        if (binding.passwordEditText.text.toString() != "1234") {
            return@setOnClickListener
        }
        okCallback()
        dialog.dismiss()
    }
    return dialog
}

fun showPasswordOpenDialog(context: Context, okCallback: () -> Unit): AlertDialog {
    return showPasswordDialog(context, okCallback, R.string.password_open)
}

fun showPasswordUnlockDialog(context: Context, okCallback: () -> Unit): AlertDialog {
    return showPasswordDialog(context, okCallback, R.string.password_unlock)
}
