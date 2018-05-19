package com.dmi.kotlintest.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

open class SimpleTextWatcher(
        private val control: EditText,
        private val textChanged: ((s: CharSequence?) -> Unit)? = null
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChanged?.let { it(s) }
    }
}