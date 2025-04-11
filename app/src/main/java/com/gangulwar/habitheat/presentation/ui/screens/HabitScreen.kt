package com.gangulwar.habitheat.presentation.ui.screens

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
import com.gangulwar.habitheat.presentation.viewmodel.HabitViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.presentation.ui.components.HabitCard
import com.gangulwar.habitheat.utils.AppColors
import com.gangulwar.habitheat.utils.AppDimensions

@Composable
fun HabitHeatApp(
    modifier: Modifier
) {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(modifier = modifier, navController = navController, startDestination = "home") {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onAddHabit: (name: String, emoji: String?, description: String?) -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var habitEmoji by remember { mutableStateOf("") }
    var habitDescription by remember { mutableStateOf("") }

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
                Box(modifier = Modifier.fillMaxWidth()) {
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

                Text("Habit Name", fontWeight = FontWeight.Medium, color = AppColors.Popover.Foreground)
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

                Text("Icon (optional)", fontWeight = FontWeight.Medium, color = AppColors.Popover.Foreground)
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

                Spacer(modifier = Modifier.height(16.dp))

                Text("Description (optional)", fontWeight = FontWeight.Medium, color = AppColors.Popover.Foreground)
                OutlinedTextField(
                    value = habitDescription,
                    onValueChange = { habitDescription = it },
                    placeholder = { Text("Briefly describe the habit", color = AppColors.Muted.Foreground) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 3,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = AppColors.Primary.Default,
                        unfocusedBorderColor = AppColors.Input,
                        cursorColor = AppColors.Primary.Default,
                    )
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
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(AppDimensions.RadiusSm)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (habitName.isNotBlank()) {
                                onAddHabit(
                                    habitName,
                                    habitEmoji.takeIf { it.isNotEmpty() },
                                    habitDescription.takeIf { it.isNotBlank() }
                                )
                            }
                        },
                        enabled = habitName.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary.Default,
                            contentColor = AppColors.Primary.Foreground,
                            disabledContainerColor = AppColors.Muted.Default,
                            disabledContentColor = AppColors.Muted.Foreground
                        ),
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(AppDimensions.RadiusSm)
                    ) {
                        Text("Add Habit")
                    }
                }
            }
        }
    }
}