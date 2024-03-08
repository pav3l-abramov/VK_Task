package com.example.vktask.common.composable

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.example.vktask.data.DataToItemScreen
import com.example.vktask.data.Product
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
    TaskCardMain(content, imgUrl, description, price, onEditClick, modifier)
}

@SuppressLint("SuspiciousIndentation")
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
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
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
fun CardDetail(
    product: Product
) {
    val listItemProduct = listOf(
        DataToItemScreen("Description: ", product.description),
        DataToItemScreen("Price: ", product.price.toString()),
        DataToItemScreen("Discount: ", product.discountPercentage.toString()),
        DataToItemScreen("Rating: ", product.rating.toString()),
        DataToItemScreen("Stock: ", product.stock.toString()),
        DataToItemScreen("Brand: ", product.brand.toString()),
        DataToItemScreen("Category: ", product.category.toString())
    )
    Column(Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),){
                product.images?.let { ImageSlider(it) }
        }
        listItemProduct.forEach{
            TaskDetailCard(it.name,it.properties)
        }


    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })
    HorizontalPager(state = pagerState) { page ->
        DrawImageDetail(images[page], Modifier)
    }
}
@Composable
fun DrawImageDetail(
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
                    .clip(shape = RectangleShape),
                contentScale = ContentScale.Crop
            )
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

@Composable
fun TaskDetailCard(
    contentFirst: String,
    contentSecond: String
) {
    Card(
        modifier = Modifier.padding(0.dp,2.dp)
    ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = contentFirst,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (contentSecond != "null") {
                            Text(
                                text = contentSecond,
                            )

                    } else {
                        Text(
                            text = "no data"
                        )
                    }
                }
            }
        }

}


@Preview
@Composable
fun qeqq() {
    CardDetail(
        product = Product(
            1,
            "title",
            "description",
            100,
            10f,
            3f,
            10,
            "brand",
            "category",
            "thumbnail",
            listOf("", "")
        )
    )
}