package com.ally.care.ui.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateTime(millis: Long): String =
    SimpleDateFormat("EEE, MMM d 'at' h:mm a", Locale.getDefault()).format(Date(millis))

fun formatDate(millis: Long): String =
    SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date(millis))

fun formatTime(millis: Long): String =
    SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(millis))
