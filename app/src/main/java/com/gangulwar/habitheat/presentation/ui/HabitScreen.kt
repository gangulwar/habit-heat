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

@Composable
fun HabitScreen(viewModel: HabitViewModel = koinViewModel()) {
    val habits by viewModel.habits.collectAsState()

    var name by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add Habit", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = emoji,
            onValueChange = { emoji = it },
            label = { Text("Emoji (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.addHabit(name, emoji.ifBlank { null }, description.ifBlank { null })
                name = ""; emoji = ""; description = ""
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Your Habits", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(habits) { habit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${habit.emoji.orEmpty()} ${habit.name}", style = MaterialTheme.typography.bodyLarge)
                        habit.description?.let {
                            Text("📝 $it", style = MaterialTheme.typography.bodySmall)
                        }

                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("Note (optional)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(onClick = {
                                viewModel.markCompleted(habit.id, note.ifBlank { null })
                                note = ""
                            }) {
                                Text("Mark Done")
                            }
                            Button(
                                onClick = { viewModel.deleteHabit(habit) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Delete", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
