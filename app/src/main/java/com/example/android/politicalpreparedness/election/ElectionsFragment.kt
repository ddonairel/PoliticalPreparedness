package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.repository.ElectionRepository

class ElectionsFragment : Fragment() {

    private lateinit var viewModel: ElectionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = ElectionDatabase.getInstance(application).electionDao
        val repository = ElectionRepository(dataSource)
        val viewModelFactory = ElectionsViewModelFactory(repository, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[ElectionsViewModel::class.java]

        binding.viewModel = viewModel

        binding.savedElectionsRv.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener { election ->
                viewModel.onElectionClicked(election)
            })

        binding.upcomingElectionsRv.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener { election ->
                viewModel.onElectionClicked(election)
            })

        viewModel.navigateToSavedElectionDetail.observe(viewLifecycleOwner) { election ->
            election?.let {
                findNavController().navigate(
                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        election.id,
                        election.division
                    )
                )
                viewModel.onElectionDetailNavigated()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadElections()
    }

}