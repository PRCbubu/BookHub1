import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
@TypeConverters(VolumeInfoConverter::class) // Register your converter
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
