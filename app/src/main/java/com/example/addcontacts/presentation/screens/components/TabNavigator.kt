package com.example.addcontacts.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.addcontacts.R
import com.example.addcontacts.presentation.viewmodel.AddContactsViewModel

@Composable
fun TabNavigator(viewModel: AddContactsViewModel){
    val interactionSource = remember { MutableInteractionSource() }


    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color.LightGray)
            .padding(2.dp)
            .fillMaxWidth()
    ){
        //all-contacts-tab
       Row(
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier
               .clip(RoundedCornerShape(30.dp))
               .background(viewModel.allContactsColor)
               .padding(horizontal = 42.dp, vertical = 5.dp)
               .clickable(
                   interactionSource = interactionSource,
                   indication = null,
                   onClick = {
                      viewModel.onAllSelected()

                   }
               )

       ) {
           Icon(painter = painterResource(R.drawable.directory_icon),
               contentDescription = "contacts-icon", tint = Color.Gray)
           Spacer(Modifier.width(10.dp))
           Text("All", fontSize = 13.sp, fontWeight = FontWeight.Bold)
       }
        //fav-contacts-tab
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(viewModel.favouritesColor)
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .clickable(
                    onClick = {
                        viewModel.onFavouritesSelected()
                    },
                    interactionSource = interactionSource,
                    indication = null
                )

        ) {
            Icon(painter = painterResource(R.drawable.favourite),
                contentDescription = "contacts-icon", tint = Color.Gray)
            Spacer(Modifier.width(10.dp))
            Text("Favourites(0)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }



    }
}

//fun favContactsButton(){
//    Button(
//        onClick = {}
//    ) {
//        Text("Favourites")
//    }
//}