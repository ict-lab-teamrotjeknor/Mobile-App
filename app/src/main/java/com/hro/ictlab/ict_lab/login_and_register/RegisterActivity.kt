package com.hro.ictlab.ict_lab.login_and_register

import android.os.Bundle
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setActionBar(R.string.login_title, true)
    }
}
