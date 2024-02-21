import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<BookEntity>)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    // Add queries for getting a book by ID, deleting, etc. as needed 
} 
