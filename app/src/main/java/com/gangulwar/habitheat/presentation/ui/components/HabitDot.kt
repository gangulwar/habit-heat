package com.gangulwar.habitheat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gangulwar.habitheat.utils.AppColors

@Composable
fun HabitDot(
    date: String,
    isCompleted: Boolean,
    onClick: (String) -> Unit
) {
    val color = if (isCompleted) AppColors.Progress.Achieved else AppColors.Progress.None

    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color)
            .clickable { onClick(date) }
            .semantics {
                contentDescription = "$date - ${if (isCompleted) "Completed" else "No entry"}"
            }
    )
}