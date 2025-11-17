package com.example.addcontacts.presentation.screens.components

import com.example.addcontacts.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ContactCard(name:String,telNo: String){
    val initials = getInitials(name)
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ){
            //slot for the profile image
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(color = colorResource(R.color.mainTheme))
                        .size(50.dp)
                ){
                    Text(initials, color = Color.White, fontWeight = FontWeight.Bold, letterSpacing = .3.sp)

                }
                Spacer(Modifier.width(20.dp))
                //name and number column from here you reverse engineerer
                Column (
                    horizontalAlignment = Alignment.Start,

                    ){
                    //contacts names her
                    Text(name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(end = 15.dp))
                    //contacts telephone num here
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                        , verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(painter = painterResource(R.drawable.call),
                            contentDescription = "call-icon on contact card",
                            modifier = Modifier.size(15.dp),
                            tint = Color.DarkGray)

                        Text(telNo)

                    }

                }
            }
            //favourites icon here
            IconButton(
                onClick = {}

            ) {
                Icon(painter = painterResource(R.drawable.favourite),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.DarkGray
                    )
            }

        }
    }
}

fun getInitials(fullName: String): String {
    val parts = fullName.trim().split(" ")
    return if (parts.size >= 2) {
        "${parts[0].firstOrNull()?.uppercaseChar() ?: ""}${parts[1].firstOrNull()?.uppercaseChar() ?: ""}"
    } else {
        "${parts[0].firstOrNull()?.uppercaseChar() ?: ""}"
    }
}