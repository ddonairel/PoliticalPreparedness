package com.example.android.politicalpreparedness.repository

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(private val dataSource: ElectionDao) {

    suspend fun getSavedElections(): List<Election> {
        return dataSource.getElections()
    }

    suspend fun getElection(id: Int): Election? {
        return dataSource.getElection(id)
    }

    suspend fun deleteElections(id: Int) {
        dataSource.deleteElections(id)
    }

    suspend fun insertElection(election: Election) {
        dataSource.insertElection(election)
    }

    suspend fun getVoterInfo(
        address: String,
        electionId: Long,
        officialOnly: String?
    ): VoterInfoResponse {
        var voterInfoResponse: VoterInfoResponse
        withContext(Dispatchers.IO) {
            voterInfoResponse =
                CivicsApi.retrofitService.getVoterInfo(address, electionId, officialOnly)
        }
        return voterInfoResponse
    }

    suspend fun getElections(): ElectionResponse {
        var electionResponse: ElectionResponse
        withContext(Dispatchers.IO) {
            electionResponse = CivicsApi.retrofitService.getElections()
        }
        return electionResponse
    }

}