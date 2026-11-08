package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Shared app data. Every screen reads and saves through this object,
// so a change on one screen shows on the others right away.
object GatherRepository {

    const val DEMO_ORGANIZER_ID = 1

    val users = mutableStateListOf<User>()
    val events = mutableStateListOf<Event>()
    val rsvps = mutableStateListOf<Rsvp>()

    // Logged-in user (null = logged out)
    var currentUserId by mutableStateOf<Int?>(null)

    // The event shown on Screens 7 and 9
    var selectedEventId by mutableStateOf<Int?>(null)

    private var nextEventId = 1
    private var nextRsvpId = 1

    init {
        seedDemoData()
    }

    // create an event and give it a unique RSVP code
    fun createEvent(
        title: String,
        isPublic: Boolean,
        minAttendees: Int,
        maxAttendees: Int,
        dateOptions: List<DateOption>,
        invitees: List<String>
    ): Event {
        val event = Event(
            id = nextEventId++,
            organizerId = currentUserId ?: DEMO_ORGANIZER_ID,
            title = title,
            isPublic = isPublic,
            rsvpCode = generateRsvpCode(),
            minAttendees = minAttendees,
            maxAttendees = maxAttendees,
            dateOptions = dateOptions.sortedWith(compareBy({ it.date }, { it.startTime })),
            invitees = invitees
        )
        events.add(event)
        selectedEventId = event.id
        return event
    }

    fun getEvent(id: Int?): Event? = events.find { it.id == id }

    fun selectedEvent(): Event? = getEvent(selectedEventId)

    fun findEventByCode(code: String): Event? =
        events.find { it.rsvpCode.equals(code.trim(), ignoreCase = true) }

    fun myCreatedEvents(): List<Event> {
        val me = currentUserId ?: DEMO_ORGANIZER_ID
        return events.filter { it.organizerId == me }
    }

    fun rsvpsFor(eventId: Int): List<Rsvp> = rsvps.filter { it.eventId == eventId }

    fun submitRsvp(eventId: Int, userId: Int?, name: String, blocks: List<TimeBlock>): Rsvp {
        val rsvp = Rsvp(nextRsvpId++, eventId, userId, name, blocks)
        rsvps.add(rsvp)
        return rsvp
    }

    // 6 random characters, skipping ones that look alike (O/0, I/1)
    private fun generateRsvpCode(): String {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        var code: String
        do {
            code = (1..6).map { chars.random() }.joinToString("")
        } while (events.any { it.rsvpCode == code })
        return code
    }

    private fun seedDemoData() {
        users.addAll(
            listOf(
                User(1, "Brent", "brent@gather.app"),
                User(2, "Ivor", "ivor@gather.app"),
                User(3, "Kiana", "kiana@gather.app"),
                User(4, "Becky", "becky@gather.app")
            )
        )

        val demo = Event(
            id = nextEventId++,
            organizerId = DEMO_ORGANIZER_ID,
            title = "COMP3074 Study Session",
            isPublic = false,
            rsvpCode = "A1A1A1",
            minAttendees = 3,
            maxAttendees = 6,
            dateOptions = listOf(
                DateOption("2026-10-24", "18:00", "22:00"),
                DateOption("2026-10-25", "13:00", "17:00"),
                DateOption("2026-10-26", "09:00", "12:00")
            ),
            invitees = listOf("sam@gather.app", "jordan@gather.app", "taylor@gather.app")
        )
        events.add(demo)

        submitRsvp(demo.id, 2, "Sam", listOf(
            TimeBlock("2026-10-24", "15:00", "22:00"),
            TimeBlock("2026-10-25", "14:00", "16:00")
        ))
        submitRsvp(demo.id, 3, "Jordan", listOf(
            TimeBlock("2026-10-24", "08:00", "10:00"),
            TimeBlock("2026-10-24", "14:00", "23:59"),
            TimeBlock("2026-10-26", "09:00", "12:00")
        ))
        submitRsvp(demo.id, 4, "Taylor", listOf(
            TimeBlock("2026-10-24", "12:00", "12:30"),
            TimeBlock("2026-10-24", "15:00", "20:00"),
            TimeBlock("2026-10-25", "13:00", "17:00")
        ))
        submitRsvp(demo.id, null, "Priya (guest)", listOf(
            TimeBlock("2026-10-25", "15:00", "17:00"),
            TimeBlock("2026-10-26", "10:00", "11:30")
        ))
    }
}