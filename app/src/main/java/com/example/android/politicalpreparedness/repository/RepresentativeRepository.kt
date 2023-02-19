package com.example.android.politicalpreparedness.repository

import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepresentativeRepository {

    suspend fun getRepresentatives(address: Address): RepresentativeResponse {
        var representativeResponse: RepresentativeResponse
        withContext(Dispatchers.IO) {
            representativeResponse = CivicsApi.retrofitService.getRepresentatives(
                address.toFormattedString(),
                null,
                null,
                null
            )
        }
        return representativeResponse
    }

}