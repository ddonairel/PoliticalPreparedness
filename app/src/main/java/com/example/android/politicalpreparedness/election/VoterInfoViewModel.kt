package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val repository: ElectionRepository) : ViewModel() {

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _isElectionSaved = MutableLiveData<Boolean>()
    val isElectionSaved: LiveData<Boolean>
        get() = _isElectionSaved

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun getVoterInfo(electionId: Int, division: Division) {
        viewModelScope.launch {

            try {

                val savedElection = repository.getElection(electionId)
                val bElectionSaved = savedElection != null
                _isElectionSaved.postValue(bElectionSaved)
                if (bElectionSaved) {
                    _election.postValue(savedElection)
                    _isElectionSaved.postValue(true)
                } else {
                    val address = getAddress(division)
                    val voterInfo = repository.getVoterInfo(
                        address,
                        electionId.toLong(),
                        null
                    )
                    _election.postValue(voterInfo.election)
                    _state.postValue(voterInfo.state?.first())
                    _isElectionSaved.postValue(false)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun getAddress(division: Division): String {
        val state: String = division.state.ifEmpty { "pa" }
        val address = Address("", "", "", state, "")
        return address.toFormattedString()
    }

    fun saveElection() {
        viewModelScope.launch {
            election.value?.let {
                if (isElectionSaved.value == true) {
                    repository.deleteElections(election.value!!.id)
                    _isElectionSaved.postValue(false)
                } else {
                    repository.insertElection(election.value!!)
                    _isElectionSaved.postValue(true)
                }
            }
        }
    }

    fun loadStateUrl() {
        if (state.value?.electionAdministrationBody?.electionInfoUrl != null) {
            _url.value = state.value?.electionAdministrationBody?.electionInfoUrl!!
        }
    }

    fun loadBallotUrl() {
        if (state.value?.electionAdministrationBody?.ballotInfoUrl != null) {
            _url.value = state.value?.electionAdministrationBody?.ballotInfoUrl!!
        }
    }

}