package com.example.vktask.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesDialog(
    message: String,
    status: String,
    listStatus: List<String>,
    onNewValueCategories: (String) -> Unit,
    onCancel: () -> Unit,
    onOkButton: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    var expanded by remember { mutableStateOf(false) }
    val allListStatus = listStatus+"all"
    Dialog(
        onDismissRequest = onCancel,
    ) {
        Column(
            modifier = Modifier
                .background(color, shape = RoundedCornerShape(20.dp))
                .padding(8.dp),
        ) {
            Text(
                text = message,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = modifier
            ) {

                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                value = status,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                allListStatus.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            onNewValueCategories(item)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                }
            }
            Button(
                onClick = { onOkButton() },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Ok")

            }
        }
    }
}