package com.padmanavo.bookhub.activity

import GoogleBooksService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class DescriptionActivity : AppCompatActivity()
{
    private lateinit var txtBookName: TextView
    private lateinit var txtBookAuthor: TextView
    private lateinit var txtBookPrice: TextView
    private lateinit var txtBookRating: TextView
    private lateinit var imgBookImage: ImageView
    private lateinit var txtBookDesc: TextView
    private lateinit var btnAddToFav: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressLayout: RelativeLayout
    private lateinit var toolbar: Toolbar
    private var bookId: String? = "100"

    private val retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/books/v1/").addConverterFactory(GsonConverterFactory.create()).build()
    private val booksService = retrofit.create(GoogleBooksService::class.java)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookRating)
        imgBookImage = findViewById(R.id.imgBookImage)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        if (intent != null)
        {
            bookId = intent.getStringExtra("book_id")
        }
        else
        {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
        }
        if (bookId == "100")
        {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
        }

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity))
        {
            lifecycleScope.launch {
                try
                {
                    val response = booksService.getBookById(bookId!!, "AIzaSyBq4xrELO38-V_ypN28dsUrERouJD7jE0Y")
                    Log.d("API Response", response.body()?.volumeInfo?.imageLinks?.thumbnail.toString())
                    if(response.isSuccessful)
                    {
                        val bookResponse = response.body()
                        bookResponse?.volumeInfo?.title?.let { Log.d("response", it) }
                        Picasso.get().load(bookResponse?.volumeInfo?.imageLinks?.thumbnail).error(R.drawable.ic_book).into(imgBookImage)
                        withContext(Dispatchers.Main)
                        {
                            txtBookName.text = bookResponse?.volumeInfo?.title.toString()
                            txtBookAuthor.text = bookResponse?.volumeInfo?.authors.toString()
                            txtBookDesc.text = bookResponse?.volumeInfo?.description

                        }
                    }
                    progressLayout.visibility = View.GONE
                }
                catch (e: Exception)
                {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
        {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit"){ _, _ -> ActivityCompat.finishAffinity(this@DescriptionActivity) }
            dialog.create()
            dialog.show()
        }
    }

    /*class DBAsyncTask(val context: Context, private val bookEntity: BookEntity, private val mode: Int) : AsyncTask<Void, Void, Boolean>()
    {
        /*
        Mode 1 -> Check DB if the book is favourite or not
        Mode 2 -> Save the book into DB as favourite
        Mode 3 -> Remove the favourite book
         */

        private val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean
        {

            when (mode)
            {
                1 -> {
                    // Check DB if the book is favourite or not
                    val book: BookEntity? = db.bookDao().getBookByID(bookEntity.book_id.toString())
                    db.close()
                    return book != null
                }
                2 -> {
                    // Save the book into DB as favourite
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    // Remove the favourite book
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }*/
}