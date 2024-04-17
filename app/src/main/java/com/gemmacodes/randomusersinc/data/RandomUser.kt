package com.gemmacodes.randomusersinc.data

data class RandomUserResponse(
    val results: List<RandomUser>
)

data class RandomUser(
    val picture: Picture,
    val name: Name,
    val phone: String,
    val email: String,
    val gender: String,
    val location: Location,
    val registered: Registered,
    val id: Id
)
data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Street(
    val number: Int,
    val name: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Registered(
    val date: String,
    val age: Int
)

data class Id(
    val name: String,
    val value: String
)
