package com.example.notesave

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesave.ui.theme.NoteSaveTheme

class InnerScreen : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteSaveTheme {

                var titleText = remember { mutableStateOf("") }
                var subtitleText = remember { mutableStateOf("") }

                val poppinsFontFamily = FontFamily(Font(R.font.poppins_medium))
                val poppinsFontFamilyBold = FontFamily(Font(R.font.poppins_bold))

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {

                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Add Person",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {

                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Home",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {

                                }
                            )
                            var mydatabase = MydataClass(applicationContext)
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "More Options",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .clickable {
                                        mydatabase.insertData(title = titleText.value, subtitle = subtitleText.value)

                                        val intent = Intent(this@InnerScreen, MainActivity::class.java)
                                        startActivity(intent)
                                }
                            )
                        }
                        Text(
                            text = "03 Dec, 2024",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(start = 15.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))


                        OutlinedTextField(
                            value = titleText.value,
                            onValueChange = { titleText.value = it },
                            placeholder = {
                                Text(
                                    text = "Title",
                                    fontSize = 28.sp,
                                    fontFamily = poppinsFontFamilyBold,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 28.sp,
                                fontFamily = poppinsFontFamilyBold,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = subtitleText.value,
                            onValueChange = { subtitleText.value = it },
                            placeholder = {
                                Text(
                                    text = "Note",
                                    fontSize = 18.sp,
                                    fontFamily = poppinsFontFamily,
                                    color = Color.Gray
                                )
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontFamily = poppinsFontFamily
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    }
}
