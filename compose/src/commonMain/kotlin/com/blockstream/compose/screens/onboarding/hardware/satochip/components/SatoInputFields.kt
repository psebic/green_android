package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SatoInputFields(
    modifier: Modifier = Modifier,
    inputValues: SnapshotStateList<String>,
    satoGray: Color,
    satoGreen: Color
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        val rows = inputValues.chunked(3)

        rows.forEachIndexed { rowIndex, rowItems ->
            val rowItems = rows[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEachIndexed { columnIndex, value ->
                    val globalIndex = rowIndex * 3 + columnIndex + 1
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp)
                        ,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "$globalIndex", modifier = Modifier.padding(bottom = 4.dp, end = 4.dp), color = satoGreen)
                        TextField(
                            value = value,
                            onValueChange = { newValue ->
                                inputValues[globalIndex - 1] = newValue
                            },
                            textStyle = TextStyle(
                                color = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = satoGray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}