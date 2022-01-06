package com.pawegio.androidprofilersamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sample_1_activity.*
import org.threeten.bp.LocalDateTime

class Sample1Activity : AppCompatActivity() {

    private val listAdapter by lazy { MyAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_1_activity)

        listView.adapter = listAdapter

        swipeRefreshLayout.setOnRefreshListener(::refreshData)
    }

    private fun refreshData() {
        listAdapter.clear()
        listAdapter.addAll(generateItems())
        listAdapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }
}

private fun generateItems(): List<Item> {
    val now = LocalDateTime.now()
    return List(500) { Item(now, it + 1) }
}

data class Item(val now: LocalDateTime, val offset: Int)