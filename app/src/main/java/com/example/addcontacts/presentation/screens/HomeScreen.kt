package com.example.addcontacts.presentation.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.addcontacts.presentation.screens.components.ContactCard
import com.example.addcontacts.presentation.screens.components.TabNavigator
import com.example.addcontacts.presentation.screens.components.TopAppBar
import com.example.addcontacts.presentation.viewmodel.AddContactsViewModel
import com.example.addcontacts.R
import dagger.hilt.android.lifecycle.HiltViewModel


class HomeScreen: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewmodel: AddContactsViewModel = hiltViewModel()
        val context = LocalContext.current

       // val contacts = viewmodel.contacts.collectAsState()
       // val contacts = viewmodel.db_contacts.collectAsState(initial = emptyList())
        val contacts = viewmodel.searchResults.collectAsState()

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted){
                viewmodel.onPermissionGranted(context)
                viewmodel.loadContacts(context)
                Toast.makeText(context,"Read contacts permission granted.",Toast.LENGTH_SHORT).show()
            }else{
                viewmodel.hasPermission.value = true
                Toast.makeText(context,"Read contacts permission denied!",Toast.LENGTH_SHORT).show()
            }

        }
//        LaunchedEffect(Unit) {
//            if (!viewmodel.checkReadContactsPermission(context)) {
//                permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
//            } else {
//                viewmodel.loadContacts(context)
//            }
//        }



        Column(

            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                contacts.value.size.toString(),
                context,
                viewmodel,
                onAddContactClicked = {
                    navigator?.push(AddContactScreen())
                })
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(15.dp))
                TabNavigator(viewModel = viewmodel)
//                if (viewmodel.currentSelectedTab.value == SelectedTab.ALL) {
//                    NoContactsScreen().Content()
//                } else {
//                    NoFavouritesScreen().Content()
//                }

                Spacer(Modifier.height(15.dp))
                //display all the phones conctacts
                when {
                    //  DB is empty + no permission → show sync button
                    contacts.value.isEmpty() && !viewmodel.checkReadContactsPermission(context) -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.mainTheme)
                                ),
                                onClick = {
                                    permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                                }
                            ) {
                                Text("SYNC_PHONE_CONTACTS", color = Color.White)
                            }
                        }
                    }

                    //DB empty + permission granted → show loading instead of flashing button
                    contacts.value.isEmpty() && viewmodel.checkReadContactsPermission(context) -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            androidx.compose.material3.CircularProgressIndicator(color = colorResource(R.color.mainTheme))
                            Spacer(Modifier.height(20.dp))
                            Text("Syncing contacts...")
                        }
                    }

                    // 3️⃣ DB has contacts → show list
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(contacts.value) { item ->
                                ContactCard(item.names, item.number)
                                Spacer(Modifier.height(5.dp))
                            }
                        }
                    }
                }



            }

        }

    }
}

//Steps that i wanna follow
//launch app
//tries to display contacts from the db
//if the contacts from db are zero we show a button for the sync contacts
//when we click sync contacts we request for permission to read contacts
//id permission granted then we load the contacts

