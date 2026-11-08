package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.GatherRepository
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic.TimeUtils

// Screen 9 - EVENT CREATION SUCCESS (FR-01: shows the real RSVP code and lets you share it)
@Composable
fun Screen9Success(
    modifier: Modifier = Modifier,
    onViewEventsClicked: () -> Unit,
    onEditClicked: () -> Unit,
) {
    val context = LocalContext.current
    val event = GatherRepository.selectedEvent()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("GATHER", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
            OutlinedButton(onClick = { }) {
                Text("≡")
            }
        }

        Text("EVENT CREATION SUCCESS SCREEN", fontSize = 12.sp, color = Color.Gray)

        if (event == null) {
            Text("No event to show yet.")
            return@Column
        }

        Text("LET'S FIND A TIME TO GATHER!", style = MaterialTheme.typography.titleMedium)
        Text("\"${event.title}\" has been created successfully.")

        Text("Share your event! This event's RSVP code is:")

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            Text(event.rsvpCode, fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)
        }

        // Android share sheet (text, email, etc.)
        OutlinedButton(onClick = {
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Join ${event.title} on Gather")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "You're invited to ${event.title}! Open Gather and enter the RSVP code ${event.rsvpCode} to share when you're free."
                )
            }
            context.startActivity(Intent.createChooser(send, "Share RSVP code"))
        }) {
            Text("SHARE CODE")
        }

        // Event summary
        Text("DATE OPTIONS")
        event.dateOptions.forEachIndexed { i, option ->
            Text("Option ${i + 1}: ${TimeUtils.dateLabel(option.date)}  ${option.startTime} - ${option.endTime}")
        }
        val maxText = if (event.maxAttendees == 0) "no limit" else "${event.maxAttendees}"
        Text("Needs at least ${event.minAttendees} people · Max: $maxText")

        Text("INVITATIONS SENT")
        if (event.invitees.isEmpty()) {
            Text("No invites added. Share the code instead.", color = Color.Gray)
        }
        event.invitees.forEach { email ->
            OutlinedTextField(
                value = email,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = onViewEventsClicked, modifier = Modifier.weight(1f)) {
                Text("VIEW EVENT")
            }
            Button(onClick = onEditClicked, modifier = Modifier.weight(1f)) {
                Text("NEW EVENT")
            }
        }
    }
}