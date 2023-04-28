package com.jmzd.ghazal.foodappmvi.utils

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.foodappmvi.R


fun Spinner.setupListWithAdapter(list: MutableList<out Any>, callback: (String) -> Unit) {

    /* چون اینجا ویو داریم نیازی به پاس دادن کانتکس نداریم و از کانتکس خود ویو استفاده میکنیم*/
    /* تنظیم لایه اختصاصی برای خود اسپینر و دراپ داون */
    val adapter = ArrayAdapter(context, R.layout.item_spinner, list)
    adapter.setDropDownViewResource(R.layout.item_spinner_list)
    this.adapter = adapter

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            Log.d(TAG, "onItemSelected: ${list[p2]}")
            callback(list[p2].toString())
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            Log.d(TAG, "onNothingSelected: ")
        }

    }

}

fun View.isVisibleGone(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.visibility = View.VISIBLE
        container.visibility = View.GONE
    } else {
        this.visibility = View.GONE
        container.visibility = View.VISIBLE
    }
}

fun View.isVisibleInvisible(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.visibility = View.VISIBLE
        container.visibility = View.INVISIBLE
    } else {
        this.visibility = View.INVISIBLE
        container.visibility = View.VISIBLE
    }
}

fun RecyclerView.setupRecyclerView(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = layoutManager
    this.setHasFixedSize(true)
    this.isNestedScrollingEnabled = false
    this.adapter = adapter
}
