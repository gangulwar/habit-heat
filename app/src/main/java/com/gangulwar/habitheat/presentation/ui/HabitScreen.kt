package com.gangulwar.habitheat.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.gangulwar.habitheat.presentation.viewmodel.HabitViewModel
import org.koin.androidx.compose.koinViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion
import com.gangulwar.habitheat.utils.AppColors
import com.gangulwar.habitheat.utils.AppDimensions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitHeatApp() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HabitViewModel = koinViewModel()
) {
    val habits by viewModel.habits.collectAsState()
    var showAddHabitDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        "HabitHeat",
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Foreground
                    )
                },
                actions = {
                    IconButton(onClick = { /* Settings action */ }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = AppColors.Foreground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Background
                )
            )

            if (habits.isEmpty()) {
                EmptyHabitsScreen(onAddHabit = { showAddHabitDialog = true })
            } else {
                HabitsList(habits = habits, viewModel = viewModel)
            }
        }

        FloatingActionButton(
            onClick = { showAddHabitDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp),
            containerColor = AppColors.Primary.Default,
            contentColor = AppColors.Primary.Foreground,
            shape = CircleShape
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Habit"
            )
        }

        if (showAddHabitDialog) {
            AddHabitDialog(
                onDismiss = { showAddHabitDialog = false },
                onAddHabit = { name, emoji, description ->
                    viewModel.addHabit(name, emoji, description)
                    showAddHabitDialog = false
                }
            )
        }
    }
}

@Composable
fun EmptyHabitsScreen(onAddHabit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimensions.ContainerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No habits added yet",
            color = AppColors.Muted.Foreground,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onAddHabit,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Primary.Default,
                contentColor = AppColors.Primary.Foreground
            ),
            shape = RoundedCornerShape(AppDimensions.RadiusSm)
        ) {
            Text("Add Your First Habit")
        }
    }
}

@Composable
fun HabitsList(habits: List<Habit>, viewModel: HabitViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimensions.ContainerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(habits) { habit ->
            HabitCard(habit = habit, viewModel = viewModel)
        }
    }
}

@Composable
fun HabitCard(habit: Habit, viewModel: HabitViewModel) {
    val scope = rememberCoroutineScope()
    var completions by remember { mutableStateOf<List<HabitCompletion>>(emptyList()) }

    LaunchedEffect(habit.id) {
        scope.launch {
            completions = viewModel.repository.getStatsForHabit(habit.id)
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
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = habit.emoji ?: "😀",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = habit.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = AppColors.Card.Foreground
                )
            }

            HabitHeatmap(
                habitId = habit.id,
                completions = completions,
                onDateClick = { date ->
                    scope.launch {
                        viewModel.markCompleted(habit.id, null)
                    }
                }
            )
        }
    }
}

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
    val rows = 8

    val dateColumns = dates.chunked(rows)

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.height(180.dp)
    ) {
        dateColumns.forEach { columnDates ->
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                columnDates.forEach { date ->
                    val isCompleted = completedMap[date]?.isCompleted ?: false
                    val color = if (isCompleted) AppColors.Progress.Achieved else AppColors.Progress.None

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(1.dp))
                            .background(color)
                            .clickable { onDateClick(date) }
                    )
                }
            }
        }
    }
}

@Composable
fun <T> GridLayout(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    Column(modifier = modifier) {
        val rows = (items.size + columns - 1) / columns

        for (rowIndex in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (colIndex in 0 until columns) {
                    val itemIndex = rowIndex * columns + colIndex
                    if (itemIndex < items.size) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            content(items[itemIndex])
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onAddHabit: (name: String, emoji: String?, description: String?) -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var habitEmoji by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(AppDimensions.RadiusLg),
            color = AppColors.Popover.Default,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add New Habit",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Popover.Foreground,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = AppColors.Popover.Foreground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Habit Name",
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Popover.Foreground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = habitName,
                    onValueChange = { habitName = it },
                    placeholder = { Text("e.g., Workout, Coding, Read", color = AppColors.Muted.Foreground) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = AppColors.Primary.Default,
                        unfocusedBorderColor = AppColors.Input,
                        cursorColor = AppColors.Primary.Default,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Icon (optional)",
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Popover.Foreground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = habitEmoji,
                    onValueChange = { habitEmoji = it },
                    placeholder = { Text("Emoji or icon character", color = AppColors.Muted.Foreground) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = AppColors.Primary.Default,
                        unfocusedBorderColor = AppColors.Input,
                        cursorColor = AppColors.Primary.Default,
                    )
                )

                Text(
                    text = "Use a simple emoji, like 📚, 👍, or 🏃",
                    fontSize = 14.sp,
                    color = AppColors.Muted.Foreground,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Muted.Default,
                            contentColor = AppColors.Muted.Foreground
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(AppDimensions.RadiusSm)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (habitName.isNotBlank()) {
                                onAddHabit(habitName, habitEmoji.takeIf { it.isNotEmpty() }, null)
                            }
                        },
                        enabled = habitName.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary.Default,
                            contentColor = AppColors.Primary.Foreground,
                            disabledContainerColor = AppColors.Muted.Default,
                            disabledContentColor = AppColors.Muted.Foreground
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(AppDimensions.RadiusSm)
                    ) {
                        Text("Add Habit")
                    }
                }
            }
        }
    }
}