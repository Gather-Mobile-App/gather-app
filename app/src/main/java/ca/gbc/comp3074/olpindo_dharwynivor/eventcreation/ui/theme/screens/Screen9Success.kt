package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Screen9Success(
    modifier: Modifier = Modifier,
    onViewEventsClicked: () -> Unit,
    onEditClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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

        Text("LET'S FIND A TIME TO GATHER!", style = MaterialTheme.typography.titleMedium)
        Text("Your event has been created successfully.")

        Text("Share your event! This event's RSVP code is:")

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            Text("A1A1A1", fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)
        }

        Text("INVITATIONS SENT")

        val sentInvites = listOf("invitee@email.com", "invitee@email.com", "invitee@email.com")
        sentInvites.forEach { email ->
            OutlinedTextField(
                value = email,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = onViewEventsClicked, modifier = Modifier.weight(1f)) {
                Text("VIEW EVENTS")
            }
            Button(onClick = onEditClicked, modifier = Modifier.weight(1f)) {
                Text("EDIT THIS EVENT")
            }
        }
    }
}
