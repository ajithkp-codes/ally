package com.ally.care.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AppointmentStatus { UPCOMING, COMPLETED, CANCELLED }

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val doctorOrProvider: String,
    val location: String,
    val dateTimeMillis: Long,
    val notes: String = "",
    val status: AppointmentStatus = AppointmentStatus.UPCOMING
)
