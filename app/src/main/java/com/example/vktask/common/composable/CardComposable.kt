package com.example.vktask.common.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.vktask.common.ext.fieldModifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TaskCardMain(
    content: String,
    imgUrl: String,
    description: String,
    price: Int,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = onEditClick
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val scroll by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
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
                    val listStateContent = rememberLazyGridState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {
                        LazyHorizontalGrid(
                            state = listStateContent,
                            rows = GridCells.Fixed(1)
                        ) {
                            item {
                                Text(
                                    text = content,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    val listStateDescription = rememberLazyGridState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {
                        LazyHorizontalGrid(
                            state = listStateDescription,
                            rows = GridCells.Fixed(1)
                        ) {
                            item {
                                Text(
                                    text = description,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = "$price$"
                        )
                    }


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
        description="descriptiondescriptiondescriptiondescriptiondescriptiondescription",
        price=12121,
    modifier= Modifier.fieldModifier(),
        onEditClick = {}
    )
}