package com.ally.care.data.db

import androidx.room.TypeConverter
import com.ally.care.data.AppointmentStatus
import com.ally.care.data.RideStatus

class Converters {
    @TypeConverter
    fun fromAppointmentStatus(value: AppointmentStatus): String = value.name

    @TypeConverter
    fun toAppointmentStatus(value: String): AppointmentStatus = AppointmentStatus.valueOf(value)

    @TypeConverter
    fun fromRideStatus(value: RideStatus): String = value.name

    @TypeConverter
    fun toRideStatus(value: String): RideStatus = RideStatus.valueOf(value)
}
