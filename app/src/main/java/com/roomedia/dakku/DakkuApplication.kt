package com.roomedia.dakku

import android.app.Application

class DakkuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: DakkuApplication
    }
}
