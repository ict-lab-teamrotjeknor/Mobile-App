package com.hro.ictlab.ict_lab.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.events.EventsActivity
import com.hro.ictlab.ict_lab.events.EventsActivity.Companion.SELECTED_DATE
import com.hro.ictlab.ict_lab.handlers.NavigationDrawerHandler
import com.hro.ictlab.ict_lab.services.NotificationService
import com.hro.ictlab.ict_lab.services.NotificationService.Companion.ALERT
import kotlinx.android.synthetic.main.activity_home.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var navigationDrawerHandler = NavigationDrawerHandler()
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private val eventFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val dummyEventList : MutableList<Date> = mutableListOf(eventFormat.parse("2018-06-24"), eventFormat.parse("2018-06-21"), eventFormat.parse("2018-06-29"), eventFormat.parse("2018-07-05"))

    companion object {
        lateinit var notificationIntent: Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setActionBar(dateFormat.format(Date()), true)

        notificationIntent = Intent(this, NotificationService::class.java)
        startService(notificationIntent)

        getUserReservations()

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        navigation_view.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle.syncState()

        dummyEventList.forEach {
            compactcalendar_view.addEvent(Event(ContextCompat.getColor(this, R.color.colorAccent), it.time, "Mooi event"))
        }

        compactcalendar_view.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                AlertDialog.Builder(this@HomeActivity)
                        .setMessage(getString(R.string.reservation_popup_explaination))
                        .setPositiveButton(getString(R.string.make_reservation)) { dialog, id -> startActivity(Intent(this@HomeActivity, EventsActivity::class.java).putExtra(SELECTED_DATE, dateClicked)) }
                        .setNegativeButton(getString(R.string.cancel)) { dialog, id -> }
                        .create().show()
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                supportActionBar!!.title = dateFormat.format(firstDayOfNewMonth)
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationDrawerHandler.handleNavigationItem(item, this)

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        return false
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {}

    private fun getUserReservations() {
        api.getUserReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println(it.succeed)
                    compactcalendar_view.addEvent(Event(ContextCompat.getColor(this, R.color.colorAccent), 5894579843753544, "Mooi event"))
                }, {

                    //showDefaultAlert("Oeps", "Er is iets fout gegaan tijdens verbinden naar de server.")
                })
    }
}