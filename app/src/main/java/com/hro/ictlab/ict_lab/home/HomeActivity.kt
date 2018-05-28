package com.hro.ictlab.ict_lab.home

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
import com.hro.ictlab.ict_lab.handlers.NavigationDrawerHandler
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var navigationDrawerHandler = NavigationDrawerHandler()
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private val eventFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val dummyEventList : MutableList<Date> = mutableListOf(eventFormat.parse("2018-05-16"), eventFormat.parse("2018-05-11"), eventFormat.parse("2018-05-19"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setActionBar(dateFormat.format(Date()), true)

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
                //Toast.makeText(this@HomeActivity, compactcalendar_view.getEvents(dateClicked).get(0).data.toString(), Toast.LENGTH_SHORT).show()
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
}