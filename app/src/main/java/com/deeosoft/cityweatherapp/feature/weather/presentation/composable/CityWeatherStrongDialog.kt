package com.deeosoft.cityweatherapp.feature.weather.presentation.composable

import WeatherText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CityWeatherStrongDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, itemToBeDeleted: String){
    AlertDialog(modifier = Modifier.height(180.dp),
        title = { Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(modifier = Modifier.padding(end = 10.dp),
                imageVector = Icons.Rounded.Warning,
                contentDescription = "Warning", tint = Color.Red)
            WeatherText(fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                content = "Warning")
        }},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                WeatherText(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    content = "You are about to delete $itemToBeDeleted from your favorite list"
                )
            }
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = {WeatherText(content = "Cancel", fontSize = 14.sp, fontWeight = FontWeight.Normal)}
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                content = {WeatherText(content = "Delete", fontSize = 14.sp, fontWeight = FontWeight.Normal)}
            )
        })
}