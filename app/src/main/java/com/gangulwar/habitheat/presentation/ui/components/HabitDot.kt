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
import com.gangulwar.habitheat.data.models.HabitCompletion
import com.gangulwar.habitheat.data.models.ProgressStatus
import com.gangulwar.habitheat.utils.AppColors

@Composable
fun HabitDot(
    date: String,
    completion: HabitCompletion?,
    onClick: (String) -> Unit
) {
    if (completion != null) {
        val color = when (completion.progressStatus) {
            ProgressStatus.PRODUCTIVE.status -> AppColors.Progress.Achieved
            ProgressStatus.NEUTRAL.status -> AppColors.Progress.Neutral
            ProgressStatus.MISSED.status -> AppColors.Progress.Failed
            else -> AppColors.Progress.None
        }

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color)
                .clickable { onClick(date) }
                .semantics {
                    contentDescription = "Habit completion on $date: ${completion.progressStatus}"
                }
        )
    }else{
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(AppColors.Progress.None)
                .clickable { onClick(date) }
                .semantics {
                    contentDescription = "No habit completion on $date"
                }
        )
    }
}
