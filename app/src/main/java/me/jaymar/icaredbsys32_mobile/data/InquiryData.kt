package me.jaymar.icaredbsys32_mobile.data

//insert into `service_records` (`record_id`,`pet_id`,`service_code`,`date`,`venue`,`status`,`remarks`) values
data class InquiryData(
    val pet_id: String,
    val service_code: String,
    val date: String,
    val venue: String,
    val remark: String
) {
}