package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.DateOption
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.GatherRepository
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic.TimeUtils
import java.util.Calendar

// One date option the organizer has added (full date + times)
private data class OptionSlot(val year: Int, val month: Int, val day: Int, val start: String, val end: String) {
    // "2026-10-24" - the date format the rest of the app uses
    val isoDate: String get() = "%04d-%02d-%02d".format(year, month, day)
}

private val MONTH_NAMES = listOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

// Screen 8 - EVENT CREATION (FR-01, FR-04)
@Composable
fun Screen8EventCreation(
    modifier: Modifier = Modifier,
    onSubmitClicked: () -> Unit,
) {
    var titleText by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(false) }

    // FR-04: attendance rules
    var minAttendees by remember { mutableStateOf("2") }
    var maxAttendees by remember { mutableStateOf("") }

    // Month shown on the calendar (month is 1-12) and the day picked in it
    var viewYear by remember { mutableIntStateOf(2026) }
    var viewMonth by remember { mutableIntStateOf(10) }
    var selectedDay by remember { mutableIntStateOf(24) }

    // Date & time inputs
    var startTimeInput by remember { mutableStateOf("18:00") }
    var endTimeInput by remember { mutableStateOf("22:00") }

    // Date options and invitees added so far
    val eventTimes = remember { mutableStateListOf<OptionSlot>() }
    var emailInput by remember { mutableStateOf("") }
    val invitees = remember { mutableStateListOf<String>() }

    // Error message shown under the inputs
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            placeholder = { Text("ENTER EVENT TITLE") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isPublic, onCheckedChange = { isPublic = it })
            Text("PUBLIC EVENT")
        }

        // FR-04: minimum (quorum) and maximum attendees
        Text("ATTENDANCE RULES", style = MaterialTheme.typography.bodyMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = minAttendees,
                onValueChange = { minAttendees = it.filter(Char::isDigit) },
                label = { Text("Min people") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = maxAttendees,
                onValueChange = { maxAttendees = it.filter(Char::isDigit) },
                label = { Text("Max (optional)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Text("PICK DATE OPTIONS", style = MaterialTheme.typography.bodyMedium)

        // Calendar - use < and > to change the month
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
                        onClick = {
                            if (viewMonth == 1) { viewMonth = 12; viewYear-- } else viewMonth--
                            selectedDay = 1
                        },
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("<", color = MaterialTheme.colorScheme.primary)
                    }
                    Text("${MONTH_NAMES[viewMonth - 1]} $viewYear", style = MaterialTheme.typography.titleSmall)
                    OutlinedButton(
                        onClick = {
                            if (viewMonth == 12) { viewMonth = 1; viewYear++ } else viewMonth++
                            selectedDay = 1
                        },
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
                        Text(day, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Work out how many days this month has and which weekday it starts on
                val cal = Calendar.getInstance().apply { set(viewYear, viewMonth - 1, 1) }
                val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                val blanks = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7   // empty cells before day 1 (Monday first)

                // 0 = empty cell
                val days = List(blanks) { 0 } + (1..daysInMonth).toList()
                val cells = days + List((7 - days.size % 7) % 7) { 0 }
                cells.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        week.forEach { day ->
                            val isSelected = day == selectedDay
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .then(if (isSelected) Modifier.border(2.dp, Color.Red) else Modifier),
                                contentAlignment = Alignment.Center
                            ) {
                                if (day != 0) {
                                    Button(
                                        onClick = { selectedDay = day },
                                        modifier = Modifier.fillMaxSize(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) Color(0xFF0086FF) else Color.Transparent,
                                            contentColor = if (isSelected) Color.White else Color.Black
                                        ),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text("$day", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Time inputs + ADD
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = startTimeInput,
                onValueChange = { startTimeInput = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = { Text("Start") }
            )
            OutlinedTextField(
                value = endTimeInput,
                onValueChange = { endTimeInput = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = { Text("End") }
            )
            Button(
                onClick = {
                    val start = TimeUtils.toMinutes(startTimeInput)
                    val end = TimeUtils.toMinutes(endTimeInput)
                    val slot = OptionSlot(viewYear, viewMonth, selectedDay, startTimeInput.trim(), endTimeInput.trim())
                    errorMessage = when {
                        start == null || end == null -> "Enter times like 18:00"
                        end <= start -> "End time must be after start time"
                        slot in eventTimes -> "That date option is already added"
                        else -> {
                            eventTimes.add(slot)
                            null
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }

        // Date options added so far
        if (eventTimes.isEmpty()) {
            Text("No date options yet. Pick a day, enter times and tap ADD.", color = Color.Gray, fontSize = 13.sp)
        }
        eventTimes.sortedWith(compareBy({ it.isoDate }, { it.start })).forEach { slot ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("${TimeUtils.dateLabel(slot.isoDate)}, ${slot.year}", style = MaterialTheme.typography.labelMedium)
                        Text("${slot.start} - ${slot.end}", style = MaterialTheme.typography.bodyLarge)
                    }
                    TextButton(onClick = { eventTimes.remove(slot) }) {
                        Text("Remove", color = Color.Red)
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                val email = emailInput.trim()
                errorMessage = when {
                    !email.contains("@") || !email.contains(".") -> "Enter a valid email"
                    email in invitees -> "That email is already added"
                    else -> {
                        invitees.add(email)
                        emailInput = ""
                        null
                    }
                }
            }) {
                Text("ADD")
            }
        }

        invitees.forEach { email ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(value = email, onValueChange = {}, modifier = Modifier.weight(1f), enabled = false)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { invitees.remove(email) }) {
                    Text("REMOVE")
                }
            }
        }

        errorMessage?.let {
            Text(it, color = Color.Red)
        }

        Button(
            onClick = {
                val min = minAttendees.toIntOrNull() ?: 0
                val max = maxAttendees.toIntOrNull() ?: 0
                errorMessage = when {
                    titleText.isBlank() -> "Enter a title for the event"
                    eventTimes.isEmpty() -> "Add at least one date option"
                    min < 1 -> "Minimum people must be at least 1"
                    max != 0 && max < min -> "Max people can't be less than min"
                    else -> null
                }
                if (errorMessage == null) {
                    // FR-01: save the event (this also creates its RSVP code)
                    GatherRepository.createEvent(
                        title = titleText.trim(),
                        isPublic = isPublic,
                        minAttendees = min,
                        maxAttendees = max,
                        dateOptions = eventTimes.map {
                            DateOption(it.isoDate, it.start, it.end)
                        },
                        invitees = invitees.toList()
                    )
                    onSubmitClicked()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CREATE EVENT")
        }
    }
}