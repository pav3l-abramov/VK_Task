package com.example.vktask.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.vktask.common.ext.fieldModifier

@Composable
fun ProductCard(
    content: String,
    imgUrl: String,
    description: String,
    modifier: Modifier,
    price: Int,
    onEditClick: () -> Unit
) {
    TaskCardMain(content,imgUrl,description, price, onEditClick, modifier)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskCardMain(
    content: String,
    imgUrl: String,
    description: String,
    price: Int,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = onEditClick
    ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    DrawImage(content = imgUrl, modifier = Modifier)
                }

//                Column(
//
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.Top,
//                        horizontalArrangement = Arrangement.Start,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        if (content.isNotBlank()) {
//                            Text(
//                                text = content,
//                                modifier = Modifier.padding(12.dp),
//                                fontWeight = FontWeight.Bold
//                            )
//
//                        }
//                        Row(
//                            verticalAlignment = Alignment.Bottom,
//                            horizontalArrangement = Arrangement.End,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                        ) {
//                            Text(
//                                text = "$price$",
//
//
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//
//                }
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) {
                    Text(
                        text = content,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = price.toString(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

}

@Composable
fun DrawImage(
    content: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
    Card(
        modifier = modifier.fillMaxSize()

    ) {
        Image(
            painter = rememberAsyncImagePainter(content),
            contentDescription = "image",
                    modifier = modifier
                        .fillMaxSize()
                .clip(shape = CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
}

@Preview
@Composable
fun qeqq(){
    TaskCardMain(
        content="content",
        imgUrl="https://cdn.dummyjson.com/product-images/41/thumbnail.webp",
        description="description",
        price=12121,
    modifier= Modifier.fieldModifier(),
        onEditClick = {}
    )
}