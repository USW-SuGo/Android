package com.sugo.app.feat.ui.common

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.sugo.app.R


@BindingAdapter("entries")
fun Spinner.setEntries(entries: List<String>?) {
    entries?.run {
        val arrayAdapter =
            ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item, entries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(selectedValue: String) {
    adapter?.run {
        val position =
            (adapter as ArrayAdapter<Any>).getPosition(selectedValue)
        setSelection(position, false)
        tag = position
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Any? {
    return selectedItem
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {

    inverseBindingListener?.run {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (tag != position) {
                    inverseBindingListener.onChange()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
