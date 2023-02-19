package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ElectionsViewModel(private val repository: ElectionRepository, application: Application) :
    ViewModel() {

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    init {
    }

    private suspend fun getUpcomingElections() {
        try {
                val elections = repository.getElections()
                _upcomingElections.postValue(elections.elections)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getSavedElections() {
        _savedElections.value = repository.getSavedElections()
    }

    private val _navigateToElectionDetail = MutableLiveData<Election?>()
    val navigateToSavedElectionDetail
        get() = _navigateToElectionDetail

    fun onElectionClicked(election: Election) {
        _navigateToElectionDetail.value = election
    }

    fun onElectionDetailNavigated() {
        _navigateToElectionDetail.value = null
    }

    fun reloadElections() {
        viewModelScope.launch {
            getUpcomingElections()
            getSavedElections()
        }
    }

}