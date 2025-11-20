package com.example.addcontacts.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.addcontacts.data.local.entity.PhoneContactEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PhoneDao {
    @Query("SELECT * FROM contacts_entity")
    fun getAllContacts(): Flow<List<PhoneContactEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertContact(contacts: PhoneContactEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertContacts(contacts: List<PhoneContactEntity>)

    @Delete
    suspend fun DeleteContact(contact: PhoneContactEntity)

    @Update
    suspend fun UpdateContact(contact: PhoneContactEntity)
}