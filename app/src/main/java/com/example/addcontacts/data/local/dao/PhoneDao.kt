package com.example.addcontacts.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.addcontacts.data.local.entity.PhoneContactEntity
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List


@Dao
interface PhoneDao {
    @Query("SELECT * FROM contacts_entity")
    fun getAllContacts(): Flow<List<PhoneContactEntity>>

    @Query("SELECT * FROM contacts_entity WHERE names LIKE '%' || :query || '%' ORDER BY names ASC")
    fun getSearchedContacts(query: String): Flow<List<PhoneContactEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertContact(contacts: PhoneContactEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertContacts(contacts: List<PhoneContactEntity>)

    @Delete
    suspend fun DeleteContact(contact: PhoneContactEntity)

    @Query("DELETE FROM contacts_entity")
    suspend fun DeleteAllContacts()

    @Update
    suspend fun UpdateContact(contact: PhoneContactEntity)
}