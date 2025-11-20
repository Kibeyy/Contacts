package com.example.addcontacts.domain.repository

import com.example.addcontacts.data.local.dao.PhoneDao
import com.example.addcontacts.data.local.entity.PhoneContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhoneContactsRepository @Inject constructor(private val dao: PhoneDao ){
    fun getAllContacts(): Flow<List<PhoneContactEntity>>{
        return dao.getAllContacts()
    }

    suspend fun insertContact(contact: PhoneContactEntity){
        return dao.InsertContact(contact)

    }

    suspend fun insertContacts(contacts: List<PhoneContactEntity>){
        return dao.InsertContacts(contacts)
    }

    suspend fun deleteContact(contact: PhoneContactEntity){
        return dao.DeleteContact(contact)
    }

    suspend fun updateContact(contact: PhoneContactEntity){
        return dao.UpdateContact(contact)

    }
}