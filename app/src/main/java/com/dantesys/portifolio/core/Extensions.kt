package com.dantesys.portifolio.core

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.core.view.setPadding
import com.dantesys.portifolio.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

var TextInputLayout.text:String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

fun View.hideSoftKeyboard(){
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}

fun Context.createDialog(block:MaterialAlertDialogBuilder.() -> Unit = {}):AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    builder.setPositiveButton(R.string.ok,null)
    block(builder)
    return builder.create()
}

fun Context.createProgessBar():AlertDialog{
    return createDialog {
        val padding = this@createProgessBar.resources.getDimensionPixelOffset(R.dimen.layout_padding)
        val pBar = ProgressBar(this@createProgessBar)
        pBar.setPadding(padding)
        setView(pBar)
        setPositiveButton(null,null)
        setCancelable(false)
    }
}
