package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.repository.RepresentativeRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(private val representativeRepository: RepresentativeRepository) :
    ViewModel() {

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _showNoData = MutableLiveData<Boolean>()
    val showNoData: LiveData<Boolean>
        get() = _showNoData

    init {
        _address.value = Address("", "", "", "", "")
    }

    fun getRepresentativesByAddress(address: Address?) {
        viewModelScope.launch {
            address?.let {
                try {
                    _address.postValue(address)
                    val (offices, officials) = representativeRepository.getRepresentatives(
                        address
                    )
                    _representatives.value = offices.flatMap { office ->
                        office.getRepresentatives(officials)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                invalidateShowNoData()
            }
        }
    }

    fun getRepresentativesFields(state: String) {
        _address.value?.state = state
        getRepresentativesByAddress(address.value)
    }

    private fun invalidateShowNoData() {
        _showNoData.value = representatives.value == null || representatives.value!!.isEmpty()
    }

}
