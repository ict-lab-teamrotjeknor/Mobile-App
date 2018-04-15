package com.hro.ictlab.ict_lab.base

import android.app.Application
import com.hro.ictlab.ict_lab.koin.koinModule
import org.koin.android.ext.android.startKoin

class BaseAppilcation: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koinModule))
    }
}