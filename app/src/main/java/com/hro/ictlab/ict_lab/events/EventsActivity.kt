package com.hro.ictlab.ict_lab.events

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.retrofit.Hour
import com.hro.ictlab.ict_lab.retrofit.Reservation
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.reservation_item.view.*
import java.util.*

class EventsActivity : BaseActivity() {

    private var adapter: ReservationAdapter = ReservationAdapter(mutableListOf())
    private var hours : MutableList<Hour> = mutableListOf()
    private val layoutManager = LinearLayoutManager(this@EventsActivity)
    private val dateSelected by lazy { intent.extras.get(SELECTED_DATE) as Date }

    companion object {
        const val SELECTED_DATE = "selected_date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setActionBar(getString(R.string.reservations_title), true)

        recycler_view.layoutManager = layoutManager

        getReservations()
    }

    private fun getReservations() {
        val reservation = Reservation("Today", mutableListOf(Hour(1, "H.312", "Bob", "Development", null, true), Hour(3, "H.214", "Henk", "Skills", null, true),Hour(5, "H.314", "", "Student", "Working on the project", true), Hour(7, "H.318", "Mike", "SLC", "Gesprek over voortgang studie", true)))
        hours = reservation.hours
        for (i in 1..15) {
            if(hours.map { it.id }.contains(i)) {
            }else {
                hours.add(Hour(i, null, null, null, null, null))
            }
        }

        adapter = ReservationAdapter(hours)
        recycler_view.adapter = adapter
    }

    inner class ReservationAdapter(val items: MutableList<Hour>) : RecyclerView.Adapter<ReservationViewholder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewholder {
            return ReservationViewholder(LayoutInflater.from(this@EventsActivity).inflate(R.layout.reservation_item, parent, false))
        }

        override fun getItemViewType(position: Int): Int {
            return 0
        }

        override fun onBindViewHolder(holder: ReservationViewholder, position: Int) {
            items.sortBy { it.id }

            val item = items[position]
            holder.view.course_name.text = item.course
            holder.view.teacher.text = item.teacher
            holder.view.classroom.text = item.classroom
            holder.view.hour_id.text = item.id.toString()
            holder.view.reason.text = item.reason
        }

        override fun getItemCount() = items.count()
        fun getItem(position: Int) = items[position]

    }

    private fun onItemSelected(position: Int) {
        val item = adapter.getItem(position)
        if(item.classroom.isNullOrEmpty()) {
            AlertDialog.Builder(this@EventsActivity)
                    .setMessage(getString(R.string.reserve_popup_explaination))
                    .setPositiveButton(getString(R.string.yes)) { dialog, id -> startActivity(Intent(this@EventsActivity, ReserveActivity::class.java).putExtra(SELECTED_DATE, dateSelected)) }
                    .setNegativeButton(getString(R.string.no)) { dialog, id -> }
                    .create().show()
        }
    }

    inner class ReservationViewholder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.reservation_item.setOnClickListener { onItemSelected(adapterPosition) }
        }
    }
}
