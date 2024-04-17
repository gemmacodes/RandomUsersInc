package com.gemmacodes.randomusersinc.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

data class RandomUserResponse(
    val results: List<RandomUser>
)

@Entity
@Parcelize
data class RandomUser(
    val id: Id,
    val picture: Picture,
    val name: Name,
    val gender: String,
    val location: Location,
    val phone: String,
    val email: String,
    val registered: Registered,
) : Parcelable

@Parcelize
data class Name(
    val first: String,
    val last: String
) : Parcelable

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable

@Parcelize
data class Location(
    val street: Street,
    val city: String,
    val state: String,
) : Parcelable

@Parcelize
data class Street(
    val number: Int,
    val name: String
) : Parcelable

@Parcelize
data class Registered(
    val date: String,
) : Parcelable

@Parcelize
data class Id(
    val value: String
) : Parcelable
