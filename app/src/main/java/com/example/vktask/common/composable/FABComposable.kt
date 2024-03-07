package com.example.vktask.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vktask.R

@Composable
fun FABTaskComposable(
    onCancelFilter: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            shape = CircleShape,
            onClick = { onCancelFilter() },
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_filter_list_24),
                        contentDescription = null
                    )
                }

            },
            modifier = Modifier
                .size(55.dp)

        )
    }
}