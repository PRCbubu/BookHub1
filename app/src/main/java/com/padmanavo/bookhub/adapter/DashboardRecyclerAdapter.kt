package com.padmanavo.bookhub.adapter

import BookEntity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.activity.DescriptionActivity
import com.padmanavo.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter (private val context: Context, private val itemList: List<BookEntity>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>()
{
    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgBookImage)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.volumeInfo.title
        holder.txtBookAuthor.text = book.volumeInfo.authors.toString()
        //holder.txtBookPrice.text = 123.toString()
        holder.txtBookRating.text = book.volumeInfo.averageRating.toString()

        Picasso.get().load(book.volumeInfo.imageLinks?.thumbnail).error(R.drawable.default_book_cover)
            .into(holder.imgBookImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id", book.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}