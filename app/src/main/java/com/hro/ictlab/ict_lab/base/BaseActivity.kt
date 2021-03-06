package com.hro.ictlab.ict_lab.base

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.hro.ictlab.ict_lab.koin.sharedPrefs
import com.hro.ictlab.ict_lab.retrofit.ApiModule
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    val api by inject<ApiModule>()

    companion object {
        val SESSION_COOKIE = "session_cookie"
    }

    protected fun setActionBar(title: String, showBackArrow: Boolean) {
        val supportActionBar = supportActionBar ?: return
        supportActionBar.setDisplayHomeAsUpEnabled(showBackArrow)
        supportActionBar.title = title
    }

    protected fun hideKeyboard(v: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    protected fun showDefaultAlert(title: String, message: String) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, id ->
                    dialog.dismiss()
                }
                .create()
                .show()
    }
}