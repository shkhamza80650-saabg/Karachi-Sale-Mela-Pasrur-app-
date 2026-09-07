package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_arrivals")
data class DailyArrivalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String,
    val priceTier: String, // "Rs. 120", "Rs. 300", "Rs. 600", "Rs. 1,200"
    val arrivalDate: String,
    val description: String,
    val imageResName: String = "img_shop_interior",
    val timestamp: Long = System.currentTimeMillis()
)
