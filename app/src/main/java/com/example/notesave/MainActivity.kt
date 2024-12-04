package com.example.notesave

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesave.ui.theme.NoteSaveTheme

class MainActivity : ComponentActivity() {
    companion object
    { lateinit var data: MydataClass }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        data = MydataClass(this)
        setContent {
            NoteSaveTheme {
                val poppinsFontFamily = FontFamily(Font(R.font.poppins_medium))
                val poppinsFontFamilyBold = FontFamily(Font(R.font.poppins_bold))

                var allDatalist = fetchAllData()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                val intent = Intent(this@MainActivity, InnerScreen::class.java)
                                startActivity(intent)
                            }, containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ) {Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Contact") }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(top = 20.dp))
                    {

                    }

                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(top = 10.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(allDatalist.size) { index ->
                            Surface(shadowElevation = 10.dp ,
                                modifier = Modifier
                                    .padding(
                                        start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp
                                    )
                                    .height(200.dp)
                                    .clickable
                                    {
                                        val intent = Intent(this@MainActivity, InnerScreen::class.java)
                                        startActivity(intent)
                                    }, shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 15.dp)
                                )
                                {
                                    Text(
                                        text = "Date",
                                        fontSize = 16.sp,
                                        color = Color(0xFFA5A5A5),
                                        fontFamily = poppinsFontFamilyBold
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(top = 35.dp, start = 15.dp)
                                )
                                {
                                    Text(
                                        text = allDatalist[index].title,
                                        fontSize = 20.sp,
                                        fontFamily = poppinsFontFamilyBold
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(top = 58.dp, start = 15.dp)
                                )
                                {
                                    Text(
                                        text = allDatalist[index].subtitle,
                                        fontSize = 20.sp,
                                        fontFamily = poppinsFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchAllData(): ArrayList<DataDetailsModel>
    {
        val dataList = ArrayList<DataDetailsModel>()

        val cursor : Cursor = data.readableDatabase.rawQuery("SELECT * FROM note",null)

        cursor.use {

            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(0)
                    val title = it.getString(1)
                    val subtitle = it.getString(2)
                    var data = DataDetailsModel(
                        id = id, title = title, subtitle = subtitle
                    )
                    dataList.add(data)

                } while (it.moveToNext())
            }
        }
        return dataList
    }
}

