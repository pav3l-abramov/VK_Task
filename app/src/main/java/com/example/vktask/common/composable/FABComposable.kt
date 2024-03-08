package com.example.vktask.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vktask.R

@Composable
fun FABTaskComposable(
    onCancelMain: () -> Unit,
    onCancelPageUp:(String, Int) -> Unit,
    onCancelPageDown: (String, Int) -> Unit,
    onCancelCategories: () -> Unit,
    onButton:()->Unit,
    mainButtonOn: Boolean,
    pageIsEmpty: Boolean,
    currentPage: Int,
    color:Color
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
        ) {
        if (mainButtonOn) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.background(color, shape = RoundedCornerShape(20.dp)).padding(4.dp).width(150.dp)
            ) {
                if (currentPage != 1) {
                    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                            onCancelPageDown(
                                "all",
                                currentPage - 1
                            ); onButton()
                        }) {
                        Text("Page Down")
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = { onCancelPageDown("all", currentPage - 1); onButton() },
                            content = {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                                    contentDescription = null
                                )

                            },
                            modifier = Modifier
                                .size(55.dp)
                                .padding(5.dp)


                        )
                    }
                }
                if(pageIsEmpty) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Current page: $currentPage")
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable {
                            onCancelPageUp(
                                "all",
                                currentPage + 1
                            ); onButton()
                        }) {
                        Text("Page Up")
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = { onCancelPageUp("all", currentPage + 1); onButton() },
                            content = {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                                    contentDescription = null
                                )

                            },
                            modifier = Modifier
                                .size(55.dp)
                                .padding(5.dp)


                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().clickable { onCancelCategories() }) {
                    Text("Categories")
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = { onCancelCategories() },
                        content = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_filter_list_24),
                                    contentDescription = null,
                                )
                            }

                        },
                        modifier = Modifier
                            .size(55.dp)
                            .padding(5.dp)

                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = { onCancelMain() },
            content = {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(if (mainButtonOn) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                }

            },
            modifier= Modifier
                .size(55.dp)

        )
    }

}