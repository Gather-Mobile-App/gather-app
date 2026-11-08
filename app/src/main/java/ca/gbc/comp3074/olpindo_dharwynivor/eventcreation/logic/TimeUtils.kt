package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic

// Helpers for "HH:mm" times and "yyyy-MM-dd" dates
object TimeUtils {

    private val MONTHS = listOf(
        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    )

    // "15:30" -> 930 minutes. Returns null if the text isn't a real time.
    fun toMinutes(hhmm: String): Int? {
        val parts = hhmm.trim().split(":")
        if (parts.size != 2) return null
        val h = parts[0].toIntOrNull() ?: return null
        val m = parts[1].toIntOrNull() ?: return null
        if (h !in 0..23 || m !in 0..59) return null
        return h * 60 + m
    }

    // 930 -> "15:30"
    fun fromMinutes(minutes: Int): String =
        "%02d:%02d".format(minutes / 60, minutes % 60)

    // "2026-10-24" -> "OCTOBER 24"
    fun dateLabel(isoDate: String): String {
        val parts = isoDate.split("-")
        if (parts.size != 3) return isoDate
        val month = parts[1].toIntOrNull() ?: return isoDate
        val day = parts[2].toIntOrNull() ?: return isoDate
        return "${MONTHS[month - 1]} $day"
    }
}