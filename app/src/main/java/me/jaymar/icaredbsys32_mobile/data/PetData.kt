package me.jaymar.icaredbsys32_mobile.data

data class PetData(
    var id: String,
    var name: String,
    var age: Int,
    var gender: Char,
    var breed: String,
    var specie: String,
    var blood_type: String,
    var weight: Double
) {
    fun getGender(): String{
        if(gender.uppercaseChar() == 'M')
            return "Male"
        else if(gender.uppercaseChar() == 'F')
            return "Female"
        return "Unspecified"
    }
}