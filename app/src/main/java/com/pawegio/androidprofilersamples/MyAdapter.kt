package com.pawegio.androidprofilersamples

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup
import com.pawegio.androidprofilersamples.R
import android.widget.TextView
import com.elpassion.android.commons.recycler.basic.ViewHolderBinder
import kotlinx.android.synthetic.main.simple_list_item.view.dateView
import kotlinx.android.synthetic.main.simple_list_item.view.remainingTimeView
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

internal class MyAdapter(private val context: Context) : BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)
    private val items = mutableListOf<Item>()

    fun addAll(items: List<Item>) {
        this.items.addAll(items)
    }

    fun clear() {
        items.clear()
    }

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): Item {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].offset.toLong()
    }

    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View {
        var convertView: View? = convertView
        // if (convertView == null) {
        convertView = layoutInflater.inflate(R.layout.simple_list_item, container, false)
        // }

        val item = getItem(position)
        val date = item.now.plusDays(item.offset.toLong()).toLocalDate().atStartOfDay()
        val remainingTime = getRemainingTime(item.now, date)
        val dateText = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val remainingTimeText = context.resources.getString(R.string.remaining, remainingTime)

        (convertView?.findViewById<View>(R.id.dateView) as TextView).text = dateText
        (convertView?.findViewById<View>(R.id.remainingTimeView) as TextView).text = remainingTimeText
        return convertView
    }

    private fun getRemainingTime(start: LocalDateTime, end: LocalDateTime): String {
        val duration = Duration.between(start, end)
        val days = duration.toDays()
        val hours = duration.minusDays(days).toHours()
        val minutes = duration.minusDays(days).minusHours(hours).toMinutes()
        val seconds = duration.minusDays(days).minusHours(hours).minusMinutes(minutes).seconds
        return buildString {
            if (days > 0) append("$days d")
            if (hours > 0) append(" $hours h")
            if (minutes > 0) append(" $minutes min")
            if (seconds > 0) append(" $seconds s")
        }.trim()
    }
}