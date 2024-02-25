import com.padmanavo.bookhub.bookApiService.BooksResponse
import com.padmanavo.bookhub.model.Book
import com.padmanavo.bookhub.model.VolumeInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Response<BooksResponse> // BooksResponse is the wrapper class for Book objects

    @GET("volumes/{bookId}")
    suspend fun getBookById(@Path("bookId") bookId: String, @Query("key") apiKey: String): Response<Book>
}
