package com.padmanavo.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.adapter.FavouriteRecyclerAdapter
import com.padmanavo.bookhub.database.BookDatabase
import com.padmanavo.bookhub.database.BookEntity



class FavouritesFragment : Fragment()
{
    private lateinit var recyclerFavourite: RecyclerView
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    private lateinit var txtNofavourite: TextView
    private var dbBookList = listOf<BookEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        txtNofavourite = view.findViewById(R.id.txtNofavourite)

        layoutManager = GridLayoutManager(activity as Context, 2)

        dbBookList = RetrieveFavourites(activity as Context).execute().get()

        if (activity != null)
        {
            if (dbBookList.isEmpty())
            {
                progressLayout.visibility = View.GONE
                txtNofavourite.visibility = View.VISIBLE
            }
            else
            {
                progressLayout.visibility = View.GONE
                txtNofavourite.visibility = View.GONE
                recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbBookList)
                recyclerFavourite.adapter = recyclerAdapter
                recyclerFavourite.layoutManager = layoutManager
            }

        }

        return view
    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {

        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()
            return db.bookDao().getAllBooks()
        }

    }

}