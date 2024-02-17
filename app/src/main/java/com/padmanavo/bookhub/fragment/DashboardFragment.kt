package com.padmanavo.bookhub.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.adapter.DashboardRecyclerAdapter
import com.padmanavo.bookhub.model.Book
import com.padmanavo.bookhub.util.ConnectionManager
import org.json.JSONException
import java.util.Collections

class DashboardFragment : Fragment()
{
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var mcontext: Context


    val bookInfoList = arrayListOf<Book>()
    private var ratingComparator = Comparator<Book> { book1, book2 ->

        if (book1.bookRating.compareTo(book2.bookRating, true) == 0) {
            book1.bookName.compareTo(book2.bookName, true)
        } else {
            book1.bookRating.compareTo(book2.bookRating, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mcontext = requireContext()
        val url = "http://13.235.250.119/v1/book/fetch_books/"
        setHasOptionsMenu(true)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(mcontext)


        if (ConnectionManager().checkConnectivity(mcontext))
        {

            val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
                Response.Listener
                {
                    // Here we will handle the response
                    try
                    {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val bookJsonObject = data.getJSONObject(i)
                                val bookObject = Book(
                                    bookJsonObject.getString("book_id"),
                                    bookJsonObject.getString("name"),
                                    bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),
                                    bookJsonObject.getString("price"),
                                    bookJsonObject.getString("image")
                                )
                                bookInfoList.add(bookObject)
                                recyclerAdapter =
                                    DashboardRecyclerAdapter(mcontext, bookInfoList)

                                recyclerDashboard.adapter = recyclerAdapter

                                recyclerDashboard.layoutManager = layoutManager

                            }
                        } else {
                            Toast.makeText(
                                mcontext,
                                "Some Error Occurred !!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            mcontext,
                            "Some unexpected error occurred!!! ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                Response.ErrorListener {
                    // Here we will handle the errors
                    if (activity != null) {
                        Toast.makeText(
                            mcontext,
                            "Volley error occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "5c947d4be3f496"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)

        }
        else
        {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        if (id == R.id.action_sort) {
            Collections.sort(bookInfoList, ratingComparator)
            bookInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)
    }
}