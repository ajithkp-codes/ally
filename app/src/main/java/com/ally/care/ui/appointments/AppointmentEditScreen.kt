package com.ally.care.ui.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ally.care.data.Appointment
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.common.DateTimePickerField
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentEditScreen(
    viewModel: AllyViewModel,
    appointmentId: Long?,
    onDone: () -> Unit
) {
    var loaded by remember { mutableStateOf(appointmentId == null) }
    var existing by remember { mutableStateOf<Appointment?>(null) }

    var title by remember { mutableStateOf("") }
    var provider by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var dateTimeMillis by remember {
        mutableStateOf(
            Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
        )
    }

    LaunchedEffect(appointmentId) {
        if (appointmentId != null) {
            val appt = viewModel.getAppointment(appointmentId)
            existing = appt
            if (appt != null) {
                title = appt.title
                provider = appt.doctorOrProvider
                location = appt.location
                notes = appt.notes
                dateTimeMillis = appt.dateTimeMillis
            }
            loaded = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (appointmentId == null) "New Appointment" else "Edit Appointment") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (!loaded) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Appointment title") },
                placeholder = { Text("e.g. Cardiology check-up") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = provider,
                onValueChange = { provider = it },
                label = { Text("Doctor / Hospital") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location / Address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            DateTimePickerField(
                label = "Date & time",
                dateTimeMillis = dateTimeMillis,
                onDateTimeChange = { dateTimeMillis = it }
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                minLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.saveAppointment(
                        (existing ?: Appointment(
                            title = "",
                            doctorOrProvider = "",
                            location = "",
                            dateTimeMillis = dateTimeMillis
                        )).copy(
                            title = title.trim(),
                            doctorOrProvider = provider.trim(),
                            location = location.trim(),
                            notes = notes.trim(),
                            dateTimeMillis = dateTimeMillis
                        )
                    )
                    onDone()
                },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save appointment")
            }
        }
    }
}
