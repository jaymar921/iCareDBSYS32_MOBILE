package me.jaymar.icaredbsys32_mobile.data

import java.util.*

data class Inquiry(
    var record_id: String,
    var pet_id: String,
    var service: String,
    var schedule_date: Date,
    var venue: String,
    var status: String,
    var remarks: String) {
}