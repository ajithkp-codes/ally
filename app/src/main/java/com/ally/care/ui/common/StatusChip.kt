package com.ally.care.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ally.care.data.AppointmentStatus
import com.ally.care.data.RideStatus

@Composable
fun statusColor(status: AppointmentStatus): Color = when (status) {
    AppointmentStatus.UPCOMING -> MaterialTheme.colorScheme.primary
    AppointmentStatus.COMPLETED -> Color(0xFF6B7280)
    AppointmentStatus.CANCELLED -> Color(0xFFB3261E)
}

@Composable
fun statusColor(status: RideStatus): Color = when (status) {
    RideStatus.REQUESTED -> Color(0xFFE8A33D)
    RideStatus.CONFIRMED -> MaterialTheme.colorScheme.primary
    RideStatus.COMPLETED -> Color(0xFF6B7280)
    RideStatus.CANCELLED -> Color(0xFFB3261E)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusLabel(text: String, color: Color) {
    SuggestionChip(
        onClick = {},
        enabled = false,
        label = { Text(text) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            disabledContainerColor = color.copy(alpha = 0.15f),
            disabledLabelColor = color
        )
    )
}
