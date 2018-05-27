package com.hro.ictlab.ict_lab.login_and_register

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import com.hro.ictlab.ict_lab.retrofit.AuthenticationForm
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Response
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        setActionBar(getString(R.string.login_title), true)

        login_button.setOnClickListener {
            authenticateUser()
        }
    }

    @OnTextChanged(R.id.password, R.id.username)
    fun checkRequiredFields() {
        login_button.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
    }

    private fun authenticateUser() {
        api.signIn(AuthenticationForm(username.text.toString().trim(), password.text.toString().trim()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.succeed) {
                        setAccessToken("kanflkasjflksaj32u823f983h932")
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        showDefaultAlert("Oeps", "Incorrect gebruikersnaam of wachtwoord.")
                    }
                }, {
                    showDefaultAlert("Oeps", "Er is iets fout gegaan tijdens verbinden naar de server.")
                })
    }
}
