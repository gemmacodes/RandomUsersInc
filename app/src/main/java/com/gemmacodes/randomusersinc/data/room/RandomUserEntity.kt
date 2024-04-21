package com.gemmacodes.randomusersinc.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_users")
data class RandomUser(
    @PrimaryKey val id: String,
    @ColumnInfo("picture") val picture: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("surname") val surname: String,
    @ColumnInfo("phone") val phone: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("gender") val gender: String,
    @ColumnInfo("street_name") val streetName: String,
    @ColumnInfo("street_number") val streetNumber: Int,
    @ColumnInfo("city") val city: String,
    @ColumnInfo("state") val state: String,
    @ColumnInfo("registered") val registered: String,
)