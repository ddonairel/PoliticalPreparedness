package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert
    suspend fun insertElection(election: Election)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElections(elections: List<Election>)

    @Query("SELECT * FROM election_table")
    suspend fun getElections(): List<Election>

    @Query("SELECT * FROM election_table where id = :id")
    suspend fun getElection(id: Int): Election?

    @Query("DELETE FROM election_table where id = :id")
    suspend fun deleteElections(id: Int)

    @Query("DELETE FROM election_table")
    suspend fun clear()

}