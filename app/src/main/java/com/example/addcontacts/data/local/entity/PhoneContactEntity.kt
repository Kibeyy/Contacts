package com.example.addcontacts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_entity")
class PhoneContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    val number : String,
    val names : String
)