package com.example.addcontacts.presentation.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addcontacts.data.local.entity.PhoneContactEntity
import com.example.addcontacts.domain.PhoneContact
import com.example.addcontacts.domain.repository.PhoneContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SelectedTab {
    ALL,
    FAVOURITES
}
sealed class ScreenStates(

)

@HiltViewModel
class AddContactsViewModel @Inject constructor(private val repo: PhoneContactsRepository) :
    ViewModel() {

    private val _contacts = MutableStateFlow<List<PhoneContact>>(emptyList())
    val contacts = _contacts.asStateFlow()
    val hasPermission = mutableStateOf(false)

    val db_contacts = repo.getAllContacts()

    fun checkReadContactsPermission(context: Context): Boolean {
        val permission = Manifest.permission.READ_CONTACTS
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }
    fun onPermissionGranted(context: Context){
        hasPermission.value = true
        loadContacts(context)
    }

    fun loadContacts(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {

            val tempContactsList = mutableListOf<PhoneContact>()
            val tempContactsEntityList = mutableListOf<PhoneContactEntity>()

            // Query all phone contacts
            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, // better than SEARCH_DISPLAY_NAME_KEY
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.use { cur ->

                // Get column indexes
                val nameIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                // 🔥 SAFETY CHECKS — prevent crash
                if (nameIndex == -1 || numberIndex == -1) {
                    _contacts.value = emptyList()
                    return@launch
                }

                while (cur.moveToNext()) {

                    val name = cur.getString(nameIndex) ?: "Unknown"
                    val number = cur.getString(numberIndex) ?: ""

                    tempContactsList.add(
                        PhoneContact(
                            name = name,
                            telNo = number
                        )
                    )
                    tempContactsEntityList.add(
                        PhoneContactEntity(
                            number = number,
                            names = name
                        )
                    )
                }
            }

            // Update StateFlow
            _contacts.value = tempContactsList

            if (tempContactsEntityList.isNotEmpty()){
               repo.insertContacts(tempContactsEntityList)
            }
        }
    }

    // -----------------------------------------------------
    // TABS
    // -----------------------------------------------------
    val currentSelectedTab = mutableStateOf(SelectedTab.ALL)

    val allContactsColor: Color
        get() = if (currentSelectedTab.value == SelectedTab.ALL) Color.White else Color.Transparent

    val favouritesColor: Color
        get() = if (currentSelectedTab.value == SelectedTab.FAVOURITES) Color.White else Color.Transparent

    fun onAllSelected() {
        currentSelectedTab.value = SelectedTab.ALL
    }

    fun onFavouritesSelected() {
        currentSelectedTab.value = SelectedTab.FAVOURITES
    }
}
