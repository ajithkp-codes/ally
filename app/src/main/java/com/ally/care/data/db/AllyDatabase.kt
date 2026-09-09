package com.ally.care.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ally.care.data.Appointment
import com.ally.care.data.RideRequest

@Database(
    entities = [Appointment::class, RideRequest::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AllyDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun rideDao(): RideDao

    companion object {
        @Volatile
        private var INSTANCE: AllyDatabase? = null

        fun getInstance(context: Context): AllyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AllyDatabase::class.java,
                    "ally.db"
                ).build().also { INSTANCE = it }
            }
    }
}
