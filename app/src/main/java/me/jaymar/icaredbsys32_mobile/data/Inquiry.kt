package me.jaymar.icaredbsys32_mobile.data

import me.jaymar.icaredbsys32_mobile.Database.Database
import java.util.*

data class Inquiry(
    var record_id: String,
    var pet_id: String,
    var service: String,
    var schedule_date: Date,
    var venue: String,
    var status: String,
    var remarks: String) {

    fun toInquiryData():InquiryData{
        return InquiryData(pet_id,Database.getServiceCode(service),schedule_date.toString(),venue,remarks)
    }
}