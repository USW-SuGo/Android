package com.sugo.app.feat.ui.common

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.sugo.app.R

@BindingAdapter("entries")
fun Spinner.setEntries(entries: List<Int>?) {
    entries?.run {
        val arrayAdapter = ArrayAdapter(context, R.layout.fragment_deal, entries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}