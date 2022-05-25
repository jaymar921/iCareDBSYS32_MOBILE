package me.jaymar.icaredbsys32_mobile.data

import me.jaymar.icaredbsys32_mobile.annotation.GamitNiSya

@GamitNiSya
data class InquiryData(
    val pet_id: String,
    val service_code: String,
    val date: String,
    val venue: String,
    val remark: String
) {
}