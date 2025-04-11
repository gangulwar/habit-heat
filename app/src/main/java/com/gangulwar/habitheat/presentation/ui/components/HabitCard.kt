package com.gangulwar.habitheat.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion
import com.gangulwar.habitheat.data.models.ProgressStatus
import com.gangulwar.habitheat.presentation.viewmodel.HabitViewModel
import com.gangulwar.habitheat.utils.AppColors
import com.gangulwar.habitheat.utils.AppDimensions
import kotlinx.coroutines.launch

@Composable
fun HabitCard(habit: Habit, viewModel: HabitViewModel) {
    val scope = rememberCoroutineScope()
    var completions by remember { mutableStateOf<List<HabitCompletion>>(emptyList()) }

    LaunchedEffect(habit.id) {
        viewModel.getStatsForHabit(habit.id) {
            completions = it
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(AppDimensions.RadiusMd),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Card.Default,
            contentColor = AppColors.Card.Foreground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = habit.emoji ?: "😀",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(1.dp,Alignment.CenterVertically)
                ){
                    Text(
                        text = habit.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = AppColors.Card.Foreground
                    )

                    habit.description?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = AppColors.Card.Foreground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            HabitHeatmap(
                habitId = habit.id,
                completions = completions,
                onDateClick = { date ->
                    scope.launch {
                        viewModel.markCompleted(habit.id, null, ProgressStatus.PRODUCTIVE)
                    }
                }
            )
        }
    }
}