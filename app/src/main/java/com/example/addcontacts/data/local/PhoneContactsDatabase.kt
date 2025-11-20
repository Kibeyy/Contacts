package com.example.addcontacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.addcontacts.data.local.dao.PhoneDao
import com.example.addcontacts.data.local.entity.PhoneContactEntity

@Database(entities = [PhoneContactEntity::class], version = 1, exportSchema = true)
abstract class PhoneContactsDatabase: RoomDatabase() {
    abstract fun phone_dao(): PhoneDao
}