package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.repository.RepresentativeRepository

class RepresentativeViewModelFactory(
    private val representativeRepository: RepresentativeRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepresentativeViewModel::class.java)) {
            return RepresentativeViewModel(representativeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
