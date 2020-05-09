package net.yan.kotlin.promoters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.ChipGroup

@BindingAdapter("textFormater")
fun chip(chipGroup: ChipGroup, array: List<String>) {
    for (text in array) {

    }
}

@BindingAdapter("itens")
fun TextView.setNomes(item: String?) {
    item?.let {
        text = item
    }
}