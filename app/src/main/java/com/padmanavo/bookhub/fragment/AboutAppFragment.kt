package com.padmanavo.bookhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.adapter.AboutAppHashMapAdapter


class AboutAppFragment : Fragment()
{
    private lateinit var listView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_about_app, container, false)

        val listView = view.findViewById<ListView>(R.id.txtAboutAppDetails)
        val data = listOf(
            Pair("Name", "Padmanavo Roy Chaudhury"),
            Pair("Department", "CSE"),
            Pair("Roll No", "2051023"),
            Pair("Autonomy Roll no", "12620001086"),
        )

        val aboutAppHashMapAdapter = AboutAppHashMapAdapter(requireContext(), data)
        listView.adapter = aboutAppHashMapAdapter

        return view
    }

}