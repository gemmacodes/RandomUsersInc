package com.gemmacodes.randomusersinc.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeletedUserDao {

    @Insert
    suspend fun insertDeletedUserId(id: DeletedUser)

    @Query("SELECT * FROM deleted_records WHERE uuid = :id")
    suspend fun findDeletedUserById(id: String): DeletedUser?

}