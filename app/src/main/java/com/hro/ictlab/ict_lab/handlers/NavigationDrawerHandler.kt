package com.hro.ictlab.ict_lab.handlers

import android.content.Intent
import android.view.MenuItem
import com.hro.ictlab.ict_lab.R.id.nav_menu_logout
import com.hro.ictlab.ict_lab.R.id.nav_menu_remote_control
import com.hro.ictlab.ict_lab.base.BaseActivity
import com.hro.ictlab.ict_lab.login_and_register.WelcomeActivity
import com.hro.ictlab.ict_lab.remote_control.RemoteControlActivity


class NavigationDrawerHandler {

    fun handleNavigationItem(item: MenuItem, activity: BaseActivity) {

        when (item.itemId) {
            nav_menu_logout -> activity.startActivity(Intent(activity, WelcomeActivity::class.java))
            nav_menu_remote_control -> activity.startActivity(Intent(activity, RemoteControlActivity::class.java))
        }
    }
}
