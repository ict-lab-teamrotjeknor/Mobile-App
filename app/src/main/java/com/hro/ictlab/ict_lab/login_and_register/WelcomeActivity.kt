package com.hro.ictlab.ict_lab.login_and_register

import android.content.Intent
import android.os.Bundle
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import com.hro.ictlab.ict_lab.koin.sharedPrefs
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        login_button.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        register_button.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

        if(sharedPrefs(application).getString(SESSION_COOKIE, "").isNotEmpty()) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    override fun onBackPressed() {}
}
