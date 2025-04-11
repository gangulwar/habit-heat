package com.gangulwar.habitheat.presentation.ui.components.new_entry

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gangulwar.habitheat.data.models.ProgressStatus
import com.gangulwar.habitheat.presentation.ui.utils.CustomCalendarDialog
import com.gangulwar.habitheat.utils.AppColors
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewEntryBottomSheet(
    date: LocalDate,
    onSaveEntry: (ProgressStatus, String?, String) -> Unit,
    onCancel: () -> Unit,
    onChangeDate: () -> Unit
) {
    var selectedStatus by remember { mutableStateOf<ProgressStatus?>(null) }
    var note by remember { mutableStateOf("") }
    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(date) }

    if (showCalendar) {
        CustomCalendarDialog(
            initialDate = selectedDate,
            onDateSelected = { newDate ->
                selectedDate = newDate
                showCalendar = false
            },
            onDismiss = {
                showCalendar = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "hhhh on ${
                selectedDate.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                )
            } ${selectedDate.dayOfMonth}, ${selectedDate.year}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "How was your progress?",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatusOption(
                label = "Productive",
                icon = "✓",
                isSelected = selectedStatus == ProgressStatus.PRODUCTIVE,
                onClick = { selectedStatus = ProgressStatus.PRODUCTIVE }
            )

            StatusOption(
                label = "Neutral",
                icon = "○",
                isSelected = selectedStatus == ProgressStatus.NEUTRAL,
                onClick = { selectedStatus = ProgressStatus.NEUTRAL }
            )

            StatusOption(
                label = "Missed",
                icon = "✗",
                isSelected = selectedStatus == ProgressStatus.MISSED,
                onClick = { selectedStatus = ProgressStatus.MISSED }
            )
        }

        Text(
            text = "Add a note (optional)",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            placeholder = { Text("How did it go? What did you learn?") },
            maxLines = 5
        )

        OutlinedButton(
            onClick = { showCalendar = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            border = BorderStroke(1.dp, AppColors.Border),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppColors.Card.Foreground
            )
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Change Date")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                border = BorderStroke(1.dp, AppColors.Border),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.Card.Foreground
                )
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    selectedStatus?.let {
                        onSaveEntry(
                            it,
                            if (note.isBlank()) null else note,
                            selectedDate.toString()
                        )
                    }
                },
                enabled = selectedStatus != null,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary.Default,
                    contentColor = AppColors.Primary.Foreground,
                    disabledContainerColor = AppColors.Primary.Default.copy(alpha = 0.5f)
                )
            ) {
                Text("Save Entry")
            }
        }
    }
}