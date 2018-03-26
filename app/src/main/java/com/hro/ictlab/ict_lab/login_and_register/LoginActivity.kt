package com.hro.ictlab.ict_lab.login_and_register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setActionBar(R.string.login_title, true)

        login_button.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                login_button.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
            }
        })
    }
}
