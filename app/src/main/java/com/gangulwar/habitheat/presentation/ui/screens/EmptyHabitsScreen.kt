package com.gangulwar.habitheat.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gangulwar.habitheat.utils.AppColors
import com.gangulwar.habitheat.utils.AppDimensions

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