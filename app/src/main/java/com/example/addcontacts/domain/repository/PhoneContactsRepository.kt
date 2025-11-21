package com.example.addcontacts.domain.repository

import com.example.addcontacts.data.local.dao.PhoneDao
import com.example.addcontacts.data.local.entity.PhoneContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhoneContactsRepository @Inject constructor(private val dao: PhoneDao ){
    fun getAllContacts(): Flow<List<PhoneContactEntity>>{
        return dao.getAllContacts()
    }

    fun getSearchedContact(query:String): Flow<List<PhoneContactEntity>>{
        return dao.getSearchedContacts(query)
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

    suspend fun deleteAllContacts(){
        return dao.DeleteAllContacts()
    }

    suspend fun updateContact(contact: PhoneContactEntity){
        return dao.UpdateContact(contact)

    }
}