package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionItemBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback) {

    class ElectionViewHolder(private var binding: ElectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(election: Election) {
            binding.election = election
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder(
            ElectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(election)
        }
        holder.bind(election)
    }

    companion object ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class ElectionListener(val clickListener: (election: Election) -> Unit) {
        fun onClick(election: Election) = clickListener(election)
    }
}