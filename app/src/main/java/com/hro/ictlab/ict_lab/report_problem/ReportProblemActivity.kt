package com.hro.ictlab.ict_lab.report_problem

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_report_problem.*

class ReportProblemActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_problem)
        ButterKnife.bind(this)

        setActionBar(R.string.report_problem_title, true)

        send_problem_button.setOnClickListener { finish() }
    }

    @OnTextChanged(R.id.problem_subject, R.id.problem_message)
    fun checkInputFields() {
        send_problem_button.isEnabled = problem_subject.text.isNotEmpty() && problem_message.text.isNotEmpty()
    }
}
