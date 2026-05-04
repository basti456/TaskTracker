package com.example.tasktraker.ui.theme

import androidx.compose.ui.graphics.Color

val BluePrimary = Color(0xFF4A80F0)
val BackgroundLight = Color(0xFFF8FAFC)
val SurfaceLight = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1E293B)
val TextSecondary = Color(0xFF64748B)

val PriorityLow = Color(0xFFE8F5E9)
val PriorityMedium = Color(0xFFFFF3E0)
val PriorityHigh = Color(0xFFFFEBEE)

val PriorityLowText = Color(0xFF2E7D32)
val PriorityMediumText = Color(0xFFEF6C00)
val PriorityHighText = Color(0xFFC62828)

val CategoryWork = Color(0xFF4A80F0)
val CategoryPersonal = Color(0xFFF1F5F9)
val CategoryShopping = Color(0xFFF1F5F9)

val CategoryPersonalText = Color(0xFF64748B)
val CategoryShoppingText = Color(0xFF64748B)

// Add these to your Color.kt
val BackgroundDark = Color(0xFF0F172A).copy(alpha = 0.4f)     // Deep navy - complements your BluePrimary
val SurfaceDark = Color(0xFF1E293B)         // Slightly lighter navy (your TextPrimary repurposed!)
val SurfaceVariantDark = Color(0xFF334155)  // Cards, elevated surfaces
val TextPrimaryDark = Color(0xFFE2E8F0)     // Soft white - easy on eyes
val TextSecondaryDark = Color(0xFF94A3B8)   // Muted slate

// Priority colors - slightly muted for dark bg
val PriorityLowDark = Color(0xFF1B3A1F)       // Deep green bg
val PriorityMediumDark = Color(0xFF3A2800)    // Deep amber bg
val PriorityHighDark = Color(0xFF3B1212)      // Deep red bg
// PriorityLowText, MediumText, HighText → brighten slightly ↓
val PriorityLowTextDark = Color(0xFF4CAF50)
val PriorityMediumTextDark = Color(0xFFFF9800)
val PriorityHighTextDark = Color(0xFFEF5350)

// Category
val CategoryWorkDark = Color(0xFF1E3A7A)       // Darker blue
val CategoryPersonalDark = Color(0xFF1E293B)
val CategoryShoppingDark = Color(0xFF1E293B)
val CategoryPersonalTextDark = Color(0xFF94A3B8)
val CategoryShoppingTextDark = Color(0xFF94A3B8)