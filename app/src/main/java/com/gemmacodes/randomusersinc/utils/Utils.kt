package com.gemmacodes.randomusersinc.utils

import com.gemmacodes.randomusersinc.data.room.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date: Date = inputFormat.parse(this)!!
    return outputFormat.format(date)
}

object FakeData {
    val fakeUser = User(
        uuid = "0000",
        registeredDate = "2018-10-20T05:34:45.913Z",
        name = "Jane",
        surname = "Doe",
        gender = "female",
        streetName = "Street",
        streetNumber = 123,
        city = "City",
        state = "State",
        email = "jane@doe.com",
        phone = "012 345 67 89",
        pictureMedium = "https://randomuser.me/api/portraits/med/women/96.jpg",
        pictureLarge = "https://randomuser.me/api/portraits/women/96.jpg",
    )
}