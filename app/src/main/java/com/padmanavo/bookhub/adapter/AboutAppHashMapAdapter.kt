package com.padmanavo.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.padmanavo.bookhub.R

class AboutAppHashMapAdapter(private val context: Context, private val data: List<Pair<String, String>>) : BaseAdapter()
{
    override fun getCount(): Int
    {
        return data.size
    }

    override fun getItem(position: Int): Pair<String, String>
    {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.listview_aboutapp_single_row, parent, false)

        val item1 = view.findViewById<TextView>(R.id.FirstItem)
        item1.text = data[position].first

        val item2 = view.findViewById<TextView>(R.id.SecondItem)
        item2.text = data[position].second

        return view

    }

}
