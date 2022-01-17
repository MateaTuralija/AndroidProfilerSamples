package com.pawegio.androidprofilersamples

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import com.elpassion.android.commons.recycler.basic.ViewHolderBinder
import com.pawegio.androidprofilersamples.model.Article
import kotlinx.android.synthetic.main.sample_1_activity.recyclerView
import kotlinx.android.synthetic.main.sample_1_activity.swipeRefreshLayout
import kotlinx.android.synthetic.main.simple_list_item.view.titleView
import kotlinx.android.synthetic.main.simple_list_item.view.authorView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


class Sample1Activity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel>()
    private var refreshInitiated = false

    private val items = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_1_activity)

        recyclerView.adapter = basicAdapterWithLayoutAndBinder(
                items, R.layout.simple_list_item, ::bindItem
        )

        swipeRefreshLayout.setOnRefreshListener(::refreshData)
        refreshData()
        viewModel.newsResponse.observe(this, {
            items.addAll(it.articles)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun refreshData() {
        Log.d("TAG", "44 Sample1Activity.refreshData: REFRESH DATA POZVAN")

        viewModel.fetchExample()

        swipeRefreshLayout.isRefreshing = false
        if (!refreshInitiated) {
            startRefreshJob()
            refreshInitiated = true
        }
    }

    private fun startRefreshJob() {
        lifecycleScope
                .launchWhenResumed {
                    while (coroutineContext.isActive) {
                        refreshData()
                        delay(15000L)
                    }
                }
    }

    private fun bindItem(holder: ViewHolderBinder<Article>, item: Article) = with(holder.itemView) {
        titleView.text = item.title
        authorView.text = item.author
    }
}
