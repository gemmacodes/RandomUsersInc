package com.gemmacodes.randomusersinc.data

import com.gemmacodes.randomusersinc.data.room.User

data class RandomUserResponse(
    val results: List<RandomUser>
)

data class RandomUser(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val street: Street,
    val registered: Registered,
    val phone: String,
    val id: Id,
    val picture: Picture
) {
    fun toUser(): User {
        return with(this) {
            User(
                uuid = id.value,
                gender = gender,
                name = name.first,
                surname = name.last,
                streetName = location.street.name,
                streetNumber = location.street.number,
                city = location.city,
                state = location.state,
                email = email,
                registeredDate = registered.date,
                phone = phone,
                pictureMedium = picture.medium,
                pictureLarge = picture.large,
            )
        }
    }
}

data class Name(
    val first: String,
    val last: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
)

data class Street(
    val number: Int,
    val name: String
)

data class Registered(
    val date: String,
)

data class Id(
    val value: String
)

data class Picture(
    val large: String,
    val medium: String,
)



