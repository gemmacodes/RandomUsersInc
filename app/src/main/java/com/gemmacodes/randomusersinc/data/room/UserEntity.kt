package com.gemmacodes.randomusersinc.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_users")
data class User(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "registered_date") val registeredDate: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "street_number") val streetNumber: Int,
    @ColumnInfo(name = "street_name") val streetName: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "picture_medium") val pictureMedium: String,
    @ColumnInfo(name = "picture_large") val pictureLarge: String,
)