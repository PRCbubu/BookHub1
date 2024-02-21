import com.padmanavo.bookhub.bookApiService.BooksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Call<BooksResponse> // BooksResponse is the wrapper class for Book objects
}
