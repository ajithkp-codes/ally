package com.ally.care

import android.app.Application
import com.ally.care.data.AllyRepository
import com.ally.care.data.db.AllyDatabase

class AllyApplication : Application() {
    lateinit var repository: AllyRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = AllyRepository(AllyDatabase.getInstance(this))
    }
}
