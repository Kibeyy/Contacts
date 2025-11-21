package com.example.addcontacts.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.addcontacts.R

class AddContactScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        // State variables
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var company by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                //go back to main screen
                                navigator?.popUntilRoot()

                            }
                        ) {

                            Icon(painter = painterResource(R.drawable.back),
                                contentDescription = "back_button")
                        }
                    },
                    title = {
                        Column(
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 15.dp)
                        ) {
                            Text(
                                text = "New Contact",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Add a new contact",
                                fontSize = 16.sp, // Reduced slightly as per standard subtitles
                                fontWeight = FontWeight.Normal
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.mainTheme)
                    )
                )
            }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White
                    )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 1. Avatar Placeholder
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1A73E8)), // Google Blue
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "??",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // 2. Form Fields
                        FormInputField(
                            label = "First Name",
                            value = firstName,
                            onValueChange = { firstName = it },
                            icon = painterResource(R.drawable.directory_icon),
                            isRequired = true,
                            placeholder = "Enter first name"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        FormInputField(
                            label = "Last Name",
                            value = lastName,
                            onValueChange = { lastName = it },
                            icon = painterResource(R.drawable.directory_icon),
                            isRequired = true,
                            placeholder = "Enter last name"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        FormInputField(
                            label = "Phone Number",
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            icon = painterResource(R.drawable.call),
                            isRequired = true,
                            placeholder = "Enter phone number",
                            keyboardType = KeyboardType.Phone
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // 3. Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { /* Handle Cancel */ },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Cancel", color = Color(0xFF5F6368))
                            }

                            Button(
                                onClick = { /* Handle Save */ },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
                            ) {
                                Text("Save", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper composable
@Composable
fun FormInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Painter,
    placeholder: String,
    isRequired: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true
) {
    Column {
        // Label Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // FIXED: Changed 'imageVector =' to 'painter ='
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color(0xFF5F6368),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3C4043)
            )
            if (isRequired) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "*",
                    fontSize = 14.sp,
                    color = Color(0xFFD93025) // Google Red
                )
            }
        }

        // Input Field
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF757575),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF1F3F4),
                unfocusedContainerColor = Color(0xFFF1F3F4),
                disabledContainerColor = Color(0xFFF1F3F4),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = isSingleLine,
            minLines = if (isSingleLine) 1 else 3,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}