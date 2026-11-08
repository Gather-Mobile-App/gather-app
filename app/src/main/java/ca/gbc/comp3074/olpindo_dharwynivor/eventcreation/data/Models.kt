package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data

// Dates are written "yyyy-MM-dd" and times "HH:mm"

data class User(
    val id: Int,
    val username: String,
    val email: String
)

// One candidate date and time for an event ("Date Option 1, 2, 3")
data class DateOption(
    val date: String,
    val startTime: String,
    val endTime: String
)

data class Event(
    val id: Int,
    val organizerId: Int,
    val title: String,
    val isPublic: Boolean,
    val rsvpCode: String,
    val minAttendees: Int,      // min quorom
    val maxAttendees: Int,      // max quorom
    val dateOptions: List<DateOption>,
    val invitees: List<String>
)

// One free time someone submitted
data class TimeBlock(
    val date: String,
    val startTime: String,
    val endTime: String
)

// One person's answer to an event. userId is null for guests.
data class Rsvp(
    val id: Int,
    val eventId: Int,
    val userId: Int?,
    val name: String,
    val blocks: List<TimeBlock>
)