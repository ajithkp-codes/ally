package com.ally.care.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class RideStatus { REQUESTED, CONFIRMED, COMPLETED, CANCELLED }

@Entity(tableName = "rides")
data class RideRequest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pickupLocation: String,
    val dropoffLocation: String,
    val dateTimeMillis: Long,
    val purpose: String = "",
    val driverOrServiceContact: String = "",
    val notes: String = "",
    val status: RideStatus = RideStatus.REQUESTED
)
