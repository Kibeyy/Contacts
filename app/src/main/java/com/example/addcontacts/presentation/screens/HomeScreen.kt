package com.example.addcontacts.presentation.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.addcontacts.presentation.screens.components.ContactCard
import com.example.addcontacts.presentation.screens.components.TabNavigator
import com.example.addcontacts.presentation.screens.components.TopAppBar
import com.example.addcontacts.presentation.viewmodel.AddContactsViewModel
import com.example.addcontacts.R


class HomeScreen: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewmodel: AddContactsViewModel = viewModel()
        val context = LocalContext.current

        val contacts = viewmodel.contacts.collectAsState()

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted){
                viewmodel.onPermissionGranted()
                viewmodel.loadContacts(context)
                Toast.makeText(context,"Read contacts permission granted.",Toast.LENGTH_SHORT).show()
            }else{
                viewmodel.hasPermission.value = true
                Toast.makeText(context,"Read contacts permission denied!",Toast.LENGTH_SHORT).show()
            }

        }
        LaunchedEffect(Unit) {
            if (!viewmodel.checkReadContactsPermission(context)) {
                permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            } else {
                viewmodel.loadContacts(context)
            }
        }



        Column(

            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(contacts.value.size.toString())
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
                if (contacts.value.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(viewmodel.contacts.value){item ->
                            ContactCard(item.name,item.telNo)
                            Spacer(Modifier.height(5.dp))

                        }
                    }
                }else{
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center){
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.mainTheme)
                            ),
                            onClick = {permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)}
                        ) {
                            Text("LOAD_PHONE_CONTACTS", color = Color.White)
                        }
                    }
                }


            }

        }

    }
}

