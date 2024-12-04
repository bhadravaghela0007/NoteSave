package com.example.notesave

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesave.ui.theme.NoteSaveTheme

class InnerScreen : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var showDeleteDialog by mutableStateOf(false)
        setContent {
            NoteSaveTheme {

                val intentData = intent.getSerializableExtra("allData") as? DataDetailsModel
                val noteId = intentData?.id

                var titleText by remember { mutableStateOf(intentData?.title ?: "") }
                var subtitleText by remember { mutableStateOf(intentData?.subtitle ?: "") }

                val initialTitle = intentData?.title ?: ""
                val initialSubTitle = intentData?.subtitle ?: ""

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
                                    finish()
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {
                                    if (titleText == initialTitle && subtitleText == initialSubTitle) {

                                        val intent = Intent(this@InnerScreen, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this@InnerScreen,
                                            "Please save your changes before navigating Homepage.", Toast.LENGTH_SHORT).show()
                                    }

                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {

                                    val shareText = "Title: $titleText\n\nSubtitle: $subtitleText"

                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, shareText)
                                    }

                                    startActivity(Intent.createChooser(shareIntent, "Share Note"))
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Done",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {
                                    val myDatabase = MydataClass(applicationContext)
                                    if (noteId != null) {
                                        myDatabase.updateData(noteId, titleText, subtitleText)
                                    } else {

                                        myDatabase.insertData(title = titleText, subtitle = subtitleText)
                                    }
                                    val intent = Intent(this@InnerScreen, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Gray,
                                modifier = Modifier.clickable {
                                    showDeleteDialog = true
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
                            value = titleText,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            onValueChange = { titleText = it },
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
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = subtitleText,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            onValueChange = { subtitleText = it },
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
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text(text = "Confirm Deletion") },
                        text = { Text(text = "Are you sure you want to delete this Note?") },
                        confirmButton = {
                            Button(onClick = {
                                val Data = MydataClass(applicationContext)
                                Data.deleteData(intentData!!.id)

                                Toast.makeText(this@InnerScreen, "Note Deleted", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@InnerScreen, MainActivity::class.java)
                                startActivity(intent)
                                finish()

                            }) {
                                Text(text = "Delete")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text(text = "Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

