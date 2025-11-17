package com.example.addcontacts.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.addcontacts.domain.PhoneContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class SelectedTab {
    ALL,
    FAVOURITES
}
sealed class ScreenStates(

)

class AddContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val _contacts = MutableStateFlow<List<PhoneContact>>(emptyList())
    val contacts = _contacts.asStateFlow()
    val context  = getApplication<Application>()
    val hasPermission = mutableStateOf(false)
    init {
        if (checkReadContactsPermission(context)){
            hasPermission.value = true
            loadContacts(context)
            }
    }
    fun checkReadContactsPermission(context: Context): Boolean {
        val permission = android.Manifest.permission.READ_CONTACTS
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }
    fun onPermissionGranted(){
        hasPermission.value = true
        loadContacts(context)
    }

    fun loadContacts(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {

            val tempContactsList = mutableListOf<PhoneContact>()

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
                }
            }

            // Update StateFlow
            _contacts.value = tempContactsList
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
