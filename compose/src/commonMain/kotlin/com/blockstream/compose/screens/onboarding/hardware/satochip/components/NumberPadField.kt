package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPadField(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "1",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "2",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "3",
                onClick = { item ->
                    onClick(item)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "4",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "5",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "6",
                onClick = { item ->
                    onClick(item)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "7",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "8",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "9",
                onClick = { item ->
                    onClick(item)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "Cancel",
                fontsize = 18.sp,
                onClick = { item ->
                    onCancel()
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f),
                item = "0",
                onClick = { item ->
                    onClick(item)
                }
            )
            SatoButtonField(
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                item = "Back",
                isImage = true,
                onClick = { item ->
                    onDelete()
                }
            )
        }
    }
}