package com.hro.ictlab.ict_lab.login_and_register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        setActionBar(R.string.login_title, true)

        login_button.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
    }

    @OnTextChanged(R.id.password, R.id.username)
    fun checkRequiredFields() {
        login_button.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
    }
}
