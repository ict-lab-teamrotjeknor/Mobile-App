package com.hro.ictlab.ict_lab.login_and_register

import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import com.hro.ictlab.ict_lab.retrofit.AuthenticationForm
import kotlinx.android.synthetic.main.activity_register.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setActionBar(R.string.register_title, true)
        ButterKnife.bind(this)

        register_button.setOnClickListener { registerUser() }
    }

    @OnTextChanged(R.id.password, R.id.username)
    fun checkRequiredFields() {
        register_button.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
    }

    private fun registerUser() {

        api.register(AuthenticationForm(username.text.toString().trim(), password.text.toString().trim()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.succeed) {
                        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    }
                }, {
                    println(it.message)
                })
    }
}
