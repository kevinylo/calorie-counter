package com.example.calories

data class Entry(
    val name: String? = null,
    val calories: String? = null,
    val protein: String? = null,
    val fat: String? = null,
    val carb: String? = null
)

fun Entry.isValid(): Boolean {
    return name != null && !name.any { !(it.isLetter() || it == ' ') } &&
            calories != null && calories.toFloatOrNull() != null &&
            (fat.isNullOrBlank() || fat.toFloatOrNull() != null) &&
            (protein.isNullOrBlank() || protein.toFloatOrNull() != null) &&
            (carb.isNullOrBlank() || carb.toFloatOrNull() != null)
}
