package com.hro.ictlab.ict_lab.base

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.hro.ictlab.ict_lab.retrofit.ApiModule
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    val api by inject<ApiModule>()

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