package com.ally.care.nav

sealed class AllyDestination(val route: String) {
    data object Home : AllyDestination("home")
    data object Appointments : AllyDestination("appointments")
    data object Transportation : AllyDestination("transportation")

    data object AppointmentEdit : AllyDestination("appointment_edit?id={id}") {
        const val ARG_ID = "id"
        fun buildRoute(id: Long? = null) = "appointment_edit?id=${id ?: -1}"
    }

    data object RideEdit : AllyDestination("ride_edit?id={id}") {
        const val ARG_ID = "id"
        fun buildRoute(id: Long? = null) = "ride_edit?id=${id ?: -1}"
    }
}
