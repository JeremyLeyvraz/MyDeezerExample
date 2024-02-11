package com.lj.app.converter

/**
 * Converts a time duration in milliseconds to a formatted string representing minutes and seconds.
 * @param timeInMillis The time duration in milliseconds.
 * @return A formatted string representing minutes and seconds (e.g., "05:30" for 5 minutes and 30 seconds).
 */
fun formatLongToMinutesSeconds(timeInMillis: Long): String {
    val totalSeconds = timeInMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}