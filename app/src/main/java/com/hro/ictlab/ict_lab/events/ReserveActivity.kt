package com.hro.ictlab.ict_lab.events

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_reserve.*

class ReserveActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        setActionBar(getString(R.string.reserve_title), true)

        val classroomAdapter = ArrayAdapter.createFromResource(this, R.array.classrooms, android.R.layout.simple_spinner_dropdown_item)
        classroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classroom_spinner.setAdapter(classroomAdapter)

        reserve_button.setOnClickListener {
            AlertDialog.Builder(this@ReserveActivity)
                    .setMessage(getString(R.string.confirm_reservation_explanaition))
                    .setPositiveButton(getString(R.string.yes)) { dialog, id -> postReservation() }
                    .setNegativeButton(getString(R.string.no)) { dialog, id -> }
                    .create().show()
        }
    }

    private fun postReservation() {
        finish()
    }
}
