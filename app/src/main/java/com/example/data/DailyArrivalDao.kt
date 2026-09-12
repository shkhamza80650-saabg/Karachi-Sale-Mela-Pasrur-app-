package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyArrivalDao {
    @Query("SELECT * FROM daily_arrivals ORDER BY timestamp DESC")
    fun getAllArrivals(): Flow<List<DailyArrivalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArrival(arrival: DailyArrivalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(arrivals: List<DailyArrivalEntity>)

    @Query("DELETE FROM daily_arrivals WHERE id = :id")
    suspend fun deleteArrivalById(id: Int)

    @Query("UPDATE daily_arrivals SET imageResName = :newImageUri WHERE id = :id")
    suspend fun updateArrivalImage(id: Int, newImageUri: String)

    @Query("SELECT COUNT(*) FROM daily_arrivals")
    suspend fun getCount(): Int
}
