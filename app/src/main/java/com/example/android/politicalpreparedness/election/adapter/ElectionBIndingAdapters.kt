package com.example.android.politicalpreparedness.election.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.fadeIn
import com.example.android.politicalpreparedness.utils.fadeOut
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}

@BindingAdapter("followElection")
fun changeTextButtonFollow(button: Button, boolean: Boolean) {
    val context = button.context
    if (boolean) {
        button.text = context.getString(R.string.unfollow_election)
    } else {
        button.text = context.getString(R.string.follow_election)
    }
}

@BindingAdapter("formatDate")
fun formatDate(textView: TextView, date: Date?) {
    date?.let {
        val pattern = "EEEE dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        textView.text = simpleDateFormat.format(it)
    }
}

@BindingAdapter("stateVisible")
fun setStateVisible(view: View, string: String?) {
    if (view.tag == null) {
        view.tag = true
        view.visibility = if (string != null) View.VISIBLE else View.GONE
    } else {
        view.animate().cancel()
        if (string != null) {
            if (view.visibility == View.GONE)
                view.fadeIn()
        } else {
            if (view.visibility == View.VISIBLE)
                view.fadeOut()
        }
    }
}