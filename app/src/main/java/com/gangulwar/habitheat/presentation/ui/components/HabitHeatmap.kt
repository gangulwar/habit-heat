package com.gangulwar.habitheat.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gangulwar.habitheat.data.models.HabitCompletion
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HabitHeatmap(
    habitId: Long,
    completions: List<HabitCompletion>,
    onDateClick: (String) -> Unit
) {
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    val dates = remember {
        generateSequence(Calendar.getInstance()) { prev ->
            Calendar.getInstance().apply {
                timeInMillis = prev.timeInMillis
                add(Calendar.DAY_OF_YEAR, -1)
            }
        }.take(180).map { dateFormatter.format(it.time) }.toList().reversed()
    }

    val completedMap = completions.associateBy { it.date }

    val columns = 10
    val rows = 7

    val dateColumns = dates.chunked(rows)

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        listState.scrollToItem(dateColumns.lastIndex)
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(dateColumns.size) { columnIndex ->
            val columnDates = dateColumns[columnIndex]

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                columnDates.forEach { date ->
                    val isCompleted = completedMap[date]?.isCompleted ?: false

                    HabitDot(
                        date = date,
                        isCompleted = isCompleted,
                        onClick = onDateClick
                    )
                }
            }
        }
    }
}