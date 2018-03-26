package com.hro.ictlab.ict_lab.base

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager

open class BaseActivity : AppCompatActivity() {

    protected fun setActionBar(title: Int, showBackArrow: Boolean) {
        val supportActionBar = supportActionBar ?: return
        supportActionBar.setDisplayHomeAsUpEnabled(showBackArrow)
        supportActionBar.title = getString(title)
    }

    protected fun hideKeyboard(v: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}