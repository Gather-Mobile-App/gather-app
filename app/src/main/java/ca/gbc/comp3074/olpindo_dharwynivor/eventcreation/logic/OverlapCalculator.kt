package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic

import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.DateOption
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.Rsvp

// FR-02: finds the best time inside one date option
object OverlapCalculator {

    data class Result(
        val totalRsvps: Int,
        val fullyFree: Int,     // free for the whole time
        val partlyFree: Int,    // free for part of it
        val bestCount: Int,     // most people free at the same time
        val bestWindows: List<Pair<Int, Int>>   // start/end minutes of the best time(s)
    )

    fun calculate(option: DateOption, rsvps: List<Rsvp>): Result {
        val windowStart = TimeUtils.toMinutes(option.startTime) ?: 0
        val windowEnd = TimeUtils.toMinutes(option.endTime) ?: 0

        // 1. Each person's free times on this date, cut to fit inside the option's time
        val people = rsvps.map { rsvp ->
            rsvp.blocks
                .filter { it.date == option.date }
                .mapNotNull { block ->
                    val s = TimeUtils.toMinutes(block.startTime) ?: return@mapNotNull null
                    val e = TimeUtils.toMinutes(block.endTime) ?: return@mapNotNull null
                    val cs = maxOf(s, windowStart)
                    val ce = minOf(e, windowEnd)
                    if (cs < ce) cs to ce else null
                }
        }

        // 2. How many people are free the whole time vs. only part of it
        val fully = people.count { ranges -> covers(ranges, windowStart, windowEnd) }
        val partly = people.count { it.isNotEmpty() } - fully

        // 3. Cut the time window at every start/end time, then count who's free in each piece
        val points = (people.flatten().flatMap { listOf(it.first, it.second) } + windowStart + windowEnd)
            .distinct().sorted()

        var best = 0
        val windows = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until points.size - 1) {
            val s = points[i]
            val e = points[i + 1]
            val count = people.count { ranges -> ranges.any { it.first <= s && e <= it.second } }
            if (count == 0) continue
            if (count > best) {
                best = count
                windows.clear()
                windows.add(s to e)
            } else if (count == best) {
                val last = windows.last()
                if (last.second == s) windows[windows.lastIndex] = last.first to e   // join with the previous piece
                else windows.add(s to e)
            }
        }
        return Result(rsvps.size, fully, partly, best, windows)
    }

    // True if the free times cover the whole window with no gaps
    private fun covers(ranges: List<Pair<Int, Int>>, start: Int, end: Int): Boolean {
        var reached = start
        for (r in ranges.sortedBy { it.first }) {
            if (r.first > reached) return false
            reached = maxOf(reached, r.second)
        }
        return reached >= end
    }
}