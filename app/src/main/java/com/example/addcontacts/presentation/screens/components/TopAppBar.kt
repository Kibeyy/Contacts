package com.example.addcontacts.presentation.screens.components

import com.example.addcontacts.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBar(contactsCount:String){
    val searchedItem = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.mainTheme))
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column {
                Row {
                    Icon(painter = painterResource(R.drawable.directory_icon),
                        contentDescription = "contacts-icon",
                        tint = Color.White
                        )
                    Spacer(Modifier.width(8.dp))
                    Text("Contacts", fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.height(5.dp))
                //contacts-count
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                    , verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(text = contactsCount + " Contacts",fontWeight = FontWeight.Normal, color = Color.White)
                    IconButton(
                        onClick = {
                            //refresh contacts function here

                        }
                    ) {
                        //refresh contacts icon
                        Icon(painter = painterResource(R.drawable.refresh),
                            contentDescription = "refresh_icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                            )

                    }
                }


            }
            AddContactsButton(onClick = {})
        }
        Spacer(Modifier.height(10.dp))
        //search bar from here
       Row(
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Center,
           modifier = Modifier.fillMaxWidth()
       ) {
           OutlinedTextField(
               value = searchedItem.value,
               onValueChange = {searchedItem.value = it},
               modifier = Modifier
                   .fillMaxWidth()
               ,
               colors = OutlinedTextFieldDefaults.colors(
                   unfocusedBorderColor = Color.White,
                   focusedBorderColor = Color.White,
                   focusedTextColor = Color.Gray,
                   unfocusedTextColor = Color.Gray,
                   cursorColor = colorResource(R.color.mainTheme),
                   focusedContainerColor =Color.White   ,
                   unfocusedContainerColor = Color.White,



               ),
               leadingIcon = {Icon(painter = painterResource(R.drawable.search),
                   contentDescription = "search-icon", tint = Color.Gray
                   )},
               placeholder = {Text("Search contacts", color = Color.DarkGray)},
               keyboardOptions = KeyboardOptions(
                   imeAction = ImeAction.Search
               ),
               keyboardActions = KeyboardActions(
                   //trigger search action from keyboard
                   onSearch = {
                       searchedItem.value = ""
                       keyboardController?.hide()
                   }
               )



           )
       }


    }
}