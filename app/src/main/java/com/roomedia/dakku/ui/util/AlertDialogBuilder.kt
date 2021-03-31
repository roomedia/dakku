package com.roomedia.dakku.ui.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.AlertInputPasswordBinding
import com.roomedia.dakku.databinding.DialogInputTextBinding

fun showEditTextDialog(context: Context, text: String, okCallback: (String) -> Unit): AlertDialog {
    val binding = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).let { layoutInflater ->
        DialogInputTextBinding.inflate(layoutInflater)
    }
    binding.editText.apply {
        setText(text)
        requestFocus()
        selectAll()
        postDelayed(
            {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            },
            50
        )
    }
    return AlertDialog.Builder(context)
        .setView(binding.root)
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(android.R.string.ok) { _, _ ->
            okCallback(binding.editText.text.toString())
        }
        .show()
}

private fun showPasswordDialog(context: Context, okCallback: () -> Unit, @StringRes titleId: Int): AlertDialog {
    val binding = AlertInputPasswordBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).apply {
        titleTextView.text = titleTextView.text.toString()
            .format(context.getString(titleId))
    }

    val dialog = AlertDialog.Builder(context)
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

fun showConfirmDialog(context: Context, okCallback: () -> Unit): AlertDialog {
    return AlertDialog.Builder(context)
        .setMessage(R.string.rollback_diary_message)
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(android.R.string.ok) { _, _ ->
            okCallback()
        }
        .show()
}
