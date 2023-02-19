package com.example.android.politicalpreparedness.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date): String {
    val pattern = "EEEE dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(date)
}