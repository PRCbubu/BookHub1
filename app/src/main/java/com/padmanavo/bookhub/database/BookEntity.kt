import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// Assuming you may want to use Type Conversion for complex data structures
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padmanavo.bookhub.model.VolumeInfo

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String, // Book ID as primary key
    val kind: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

// Type converter for VolumeInfo (Adjust if not needed)
class VolumeInfoConverter {
    @TypeConverter
    fun fromVolumeInfo(volumeInfo: VolumeInfo): String {
        return Gson().toJson(volumeInfo)
    }

    @TypeConverter
    fun toVolumeInfo(json: String): VolumeInfo {
        val type = object : TypeToken<VolumeInfo>() {}.type
        return Gson().fromJson(json, type)
    }
}
