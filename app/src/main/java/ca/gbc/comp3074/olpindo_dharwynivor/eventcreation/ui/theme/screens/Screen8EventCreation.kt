package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Screen8EventCreation(
    modifier: Modifier = Modifier,
    onSubmitClicked: () -> Unit,
) {
    var titleText by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(false) }

    // Interactive Date & Time States
    var selectedDate by remember { mutableStateOf("24") }
    var startTimeInput by remember { mutableStateOf("18:00") }
    var endTimeInput by remember { mutableStateOf("22:00") }

    // Dynamic Time Slots and Invitees Lists
    val eventTimes = remember { mutableStateOf(listOf("18:00 - 22:00")) }

    var emailInput by remember { mutableStateOf("") }
    val invitees = remember { mutableStateOf(listOf("invitee@email.com", "invitee@email.com", "invitee@email.com")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("GATHER", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
            OutlinedButton(onClick = { }) {
                Text("≡")
            }
        }

        Text("EVENT CREATION SCREEN", fontSize = 12.sp, color = Color.Gray)

        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            placeholder = { Text("ENTER TITLE FOR SCHEDULE") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isPublic, onCheckedChange = { isPublic = it })
            Text("PUBLIC EVENT")
        }

        Text("PICK DATE OPTIONS", style = MaterialTheme.typography.bodyMedium)

        // Interactive Calendar Grid Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("<", color = MaterialTheme.colorScheme.primary)
                    }
                    Text("October 2014", style = MaterialTheme.typography.titleSmall)
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(">", color = MaterialTheme.colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    daysOfWeek.forEach { day ->
                        Text(day, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.width(32.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                val calendarWeeks = listOf(
                    listOf("1", "2", "3", "4", "5", "6", "7"),
                    listOf("8", "9", "10", "11", "12", "13", "14"),
                    listOf("15", "16", "17", "18", "19", "20", "21"),
                    listOf("22", "23", "24", "25", "26", "27", "28"),
                    listOf("29", "30", "31", "1", "2", "3", "4")
                )

                calendarWeeks.forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        week.forEach { dateStr ->
                            val isSelected = (dateStr == selectedDate)
                            val isNextMonthNum = (dateStr.toInt() < 10 && week == calendarWeeks.last())

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .then(
                                        if (isSelected) Modifier.border(2.dp, Color.Red) else Modifier
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        // Only select valid month days (ignore next-month preview numbers for simplicity)
                                        if (!isNextMonthNum) {
                                            selectedDate = dateStr
                                        }
                                    },
                                    modifier = Modifier.fillMaxSize(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) Color(0xFF0086FF) else Color.Transparent,
                                        contentColor = if (isSelected) Color.White else if (isNextMonthNum) Color.Gray else Color.Black
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(dateStr, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Time Inputs Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = startTimeInput,
                onValueChange = { startTimeInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("Start") }
            )
            OutlinedTextField(
                value = endTimeInput,
                onValueChange = { endTimeInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("End") }
            )
            Button(
                onClick = {
                    if (startTimeInput.isNotBlank() && endTimeInput.isNotBlank()) {
                        val newTimeSlot = "$startTimeInput - $endTimeInput"
                        eventTimes.value = eventTimes.value + newTimeSlot
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }

        // Dynamic Selected Time Cards
        eventTimes.value.forEach { timeSlot ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("OCTOBER $selectedDate", style = MaterialTheme.typography.labelMedium)
                        Text(timeSlot, style = MaterialTheme.typography.bodyLarge)
                    }
                    if (eventTimes.value.size > 1) {
                        TextButton(onClick = { eventTimes.value = eventTimes.value - timeSlot }) {
                            Text("Remove", color = Color.Red)
                        }
                    }
                }
            }
        }

        Text("SEND INVITES")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                placeholder = { Text("email input") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (emailInput.isNotBlank()) {
                    invitees.value = invitees.value + emailInput
                    emailInput = ""
                }
            }) {
                Text("ADD")
            }
        }

        invitees.value.forEach { email ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(value = email, onValueChange = {}, modifier = Modifier.weight(1f), enabled = false)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { invitees.value = invitees.value - email }) {
                    Text("REMOVE")
                }
            }
        }

        Button(
            onClick = onSubmitClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CREATE EVENT")
        }
    }
}