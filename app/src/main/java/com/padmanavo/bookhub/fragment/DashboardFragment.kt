package com.padmanavo.bookhub.fragment
import BookEntity
import GoogleBooksService
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.adapter.DashboardRecyclerAdapter
import com.padmanavo.bookhub.model.Book
import com.padmanavo.bookhub.util.ConnectionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections

class DashboardFragment : Fragment()
{
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var mcontext: Context

    //private val YOUR_API_KEY = "AIzaSyBq4xrELO38-V_ypN28dsUrERouJD7jE0Y"

    private val retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/books/v1/").addConverterFactory(GsonConverterFactory.create()).build()
    private val booksService = retrofit.create(GoogleBooksService::class.java)

    val bookInfoList = arrayListOf<BookEntity>()
    private var ratingComparator = Comparator<BookEntity> { book1, book2 ->

        if (book1.volumeInfo.ratingsCount!!.compareTo(book2.volumeInfo.ratingsCount!!) == 0) {
            book1.volumeInfo.title.compareTo(book2.volumeInfo.title, true)
        } else {
            book1.volumeInfo.ratingsCount.compareTo(book2.volumeInfo.ratingsCount)
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
        setHasOptionsMenu(true)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        if (ConnectionManager().checkConnectivity(mcontext))
        {
            lifecycleScope.launch{
                try
                {
                    val response = booksService.searchBooks("android", "AIzaSyBq4xrELO38-V_ypN28dsUrERouJD7jE0Y")
                    withContext(Dispatchers.Main)
                    {

                        if (response.isSuccessful)
                        {
                            val bookResponse = response.body()?.items?.map { book ->
                                // Map your Book object from the API response to a BookEntity
                                BookEntity(
                                    kind = book.kind,
                                    id = book.id,
                                    etag = book.etag,
                                    selfLink = book.selfLink,
                                    volumeInfo = book.volumeInfo
                                )
                            } ?: emptyList()
                            bookInfoList.clear()
                            bookInfoList.addAll(bookResponse)
                            recyclerAdapter=DashboardRecyclerAdapter(mcontext, bookInfoList)
                            recyclerDashboard.adapter=recyclerAdapter
                            recyclerDashboard.layoutManager=layoutManager
                            progressLayout.visibility=View.GONE
                        }
                        else
                        {
                            Toast.makeText(mcontext, "Some Error Occurred !!!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                catch (e: Exception)
                {
                    Toast.makeText(mcontext, "Some Error Occurred !!!", Toast.LENGTH_SHORT).show()
                }
            }
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