package com.gemmacodes.randomusersinc.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_records")
data class DeletedUser(
    @PrimaryKey val uuid: String
)