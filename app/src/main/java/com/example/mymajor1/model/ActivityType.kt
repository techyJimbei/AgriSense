package com.example.mymajor1.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ActivityType(val icon: ImageVector, val color: Color, val displayName: String) {
    SOWING(Icons.Default.Star, Color(0xFF4CAF50), "Sowing"),
    IRRIGATION(Icons.Default.Search, Color(0xFF2196F3), "Irrigation"),
    FERTILIZER(Icons.Default.ShoppingCart, Color(0xFFFF9800), "Fertilizer"),
    HARVEST(Icons.Default.Check, Color(0xFFE91E63), "Harvest"),
    IDLE(Icons.Default.DateRange, Color(0xFF9E9E9E), "Monitor")
}