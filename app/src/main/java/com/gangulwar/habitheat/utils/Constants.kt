package com.gangulwar.habitheat.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.animation.core.*

object AppColors {
    val Border = Color(0xFFE5E7EB)
    val Input = Color(0xFFE5E7EB)
    val Ring = Color(0xFFE5E7EB)
    val Background = Color(0xFFFFFFFF)
    val Foreground = Color(0xFF000000)

    object Primary {
        val Default = Color(0xFF6366F1)
        val Foreground = Color(0xFFFFFFFF)
    }

    object Secondary {
        val Default = Color(0xFFFACC15)
        val Foreground = Color(0xFF000000)
    }

    object Destructive {
        val Default = Color(0xFFEF4444)
        val Foreground = Color(0xFFFFFFFF)
    }

    object Muted {
        val Default = Color(0xFFF3F4F6)
        val Foreground = Color(0xFF6B7280)
    }

    object Accent {
        val Default = Color(0xFF4ADE80)
        val Foreground = Color(0xFF064E3B)
    }

    object Popover {
        val Default = Color(0xFFFFFFFF)
        val Foreground = Color(0xFF111827)
    }

    object Card {
        val Default = Color(0xFFFFFFFF)
        val Foreground = Color(0xFF1F2937)
    }

    object Sidebar {
        val Default = Color(0xFFF9FAFB)
        val Foreground = Color(0xFF1F2937)
        val Primary = Color(0xFF3B82F6)
        val PrimaryForeground = Color(0xFFFFFFFF)
        val Accent = Color(0xFF10B981)
        val AccentForeground = Color(0xFFFFFFFF)
        val Border = Color(0xFFD1D5DB)
        val Ring = Color(0xFF3B82F6)
    }

    object Progress {
        val None = Color(0xFFE5E7EB)
        val Failed = Color(0xFFF87171)
        val Procrastinated = Color(0xFFFCD34D)
        val Neutral = Color(0xFFA3E635)
        val Mixed = Color(0xFF60A5FA)
        val Achieved = Color(0xFF34D399)
    }

    object Button{
        val EnableBackground = Color(0xFF0F172A)
        val DisableBackground = Color(0xFF888B94)
        val EnableContent = Color(0xFFF8FAFC)
        val DisableContent = Color(0xFFFBFDFD)
    }
}

object AppDimensions {
    val ContainerPadding = 16.dp
    val ContainerMaxWidth = 1400.dp

    val RadiusLg: Dp = 12.dp
    val RadiusMd: Dp = 10.dp
    val RadiusSm: Dp = 8.dp
}

object AppAnimations {
    val accordionDown = tween<Float>(durationMillis = 200, easing = FastOutSlowInEasing)
    val accordionUp = tween<Float>(durationMillis = 200, easing = FastOutSlowInEasing)

    val pulseSoft = infiniteRepeatable(
        animation = keyframes {
            durationMillis = 2000
            0f at 0
            0.8f at 1000
            1f at 2000
        },
        repeatMode = RepeatMode.Restart
    )
}
