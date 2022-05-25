package me.jaymar.icaredbsys32_mobile.data

import java.util.*

data class Account(
    val acc_id: String,
    var lastname: String,
    var firstname: String,
    var birthdate: Date,
    var house_no: String,
    var street: String,
    var barangay: String,
    var city: String,
    var zip: String,
    val registry_date: Date) {

    fun getAddressLine():String{
        return "$house_no, $street, $barangay, $city, $zip"
    }

    fun getFullName():String{
        return "$firstname $lastname"
    }

    fun parseAddressFragment(addressLine: String){
        val fragments:List<String> = addressLine.split(", ")
        if (fragments.isNotEmpty()) {
            house_no = fragments[0]
            if(fragments.size>1){
                street = fragments[1]
                if(fragments.size>2){
                    barangay = fragments[2]
                    if(fragments.size>3){
                        city = fragments[3]
                        if(fragments.size>4){
                            zip = fragments[4]
                        }
                    }
                }
            }
        }
    }
}