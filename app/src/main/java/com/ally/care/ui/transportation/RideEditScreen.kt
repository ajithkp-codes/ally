package com.ally.care.ui.transportation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ally.care.data.RideRequest
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.common.DateTimePickerField
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideEditScreen(
    viewModel: AllyViewModel,
    rideId: Long?,
    onDone: () -> Unit
) {
    var loaded by remember { mutableStateOf(rideId == null) }
    var existing by remember { mutableStateOf<RideRequest?>(null) }

    var pickup by remember { mutableStateOf("") }
    var dropoff by remember { mutableStateOf("") }
    var purpose by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var dateTimeMillis by remember {
        mutableStateOf(
            Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
        )
    }

    LaunchedEffect(rideId) {
        if (rideId != null) {
            val ride = viewModel.getRide(rideId)
            existing = ride
            if (ride != null) {
                pickup = ride.pickupLocation
                dropoff = ride.dropoffLocation
                purpose = ride.purpose
                contact = ride.driverOrServiceContact
                notes = ride.notes
                dateTimeMillis = ride.dateTimeMillis
            }
            loaded = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (rideId == null) "Book a Ride" else "Edit Ride") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (!loaded) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = pickup,
                onValueChange = { pickup = it },
                label = { Text("Pickup location") },
                placeholder = { Text("e.g. Home") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dropoff,
                onValueChange = { dropoff = it },
                label = { Text("Drop-off location") },
                placeholder = { Text("e.g. City Hospital") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = purpose,
                onValueChange = { purpose = it },
                label = { Text("Purpose (optional)") },
                placeholder = { Text("e.g. Cardiology appointment") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            DateTimePickerField(
                label = "Pickup date & time",
                dateTimeMillis = dateTimeMillis,
                onDateTimeChange = { dateTimeMillis = it }
            )

            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Driver / service contact (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.saveRide(
                        (existing ?: RideRequest(
                            pickupLocation = "",
                            dropoffLocation = "",
                            dateTimeMillis = dateTimeMillis
                        )).copy(
                            pickupLocation = pickup.trim(),
                            dropoffLocation = dropoff.trim(),
                            purpose = purpose.trim(),
                            driverOrServiceContact = contact.trim(),
                            notes = notes.trim(),
                            dateTimeMillis = dateTimeMillis
                        )
                    )
                    onDone()
                },
                enabled = pickup.isNotBlank() && dropoff.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save ride")
            }
        }
    }
}
