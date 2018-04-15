package com.hro.ictlab.ict_lab.login_and_register

import android.os.Bundle
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setActionBar(R.string.register_title, true)

        register_button.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        api.register("hallo")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println(it)
                }, {
                    println(it.message)
                })
    }
}
