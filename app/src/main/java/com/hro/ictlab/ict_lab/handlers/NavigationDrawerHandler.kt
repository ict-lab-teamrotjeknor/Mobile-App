package com.hro.ictlab.ict_lab.handlers

import android.content.Intent
import android.view.MenuItem
import com.hro.ictlab.ict_lab.R.id.*
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.home.HomeActivity
import com.hro.ictlab.ict_lab.koin.sharedPrefs
import com.hro.ictlab.ict_lab.login_and_register.WelcomeActivity
import com.hro.ictlab.ict_lab.remote_control.RemoteControlActivity
import com.hro.ictlab.ict_lab.report_problem.ReportProblemActivity


class NavigationDrawerHandler {

    fun handleNavigationItem(item: MenuItem, activity: BaseActivity) {

        when (item.itemId) {
            nav_menu_logout -> {
                sharedPrefs(activity).edit().putString(BaseActivity.SESSION_COOKIE, "").apply()
                activity.stopService(HomeActivity.notificationIntent)
                activity.startActivity(Intent(activity, WelcomeActivity::class.java))
            }
            nav_menu_remote_control -> activity.startActivity(Intent(activity, RemoteControlActivity::class.java))
            nav_menu_report_problem -> activity.startActivity(Intent(activity, ReportProblemActivity::class.java))
        }
    }
}
