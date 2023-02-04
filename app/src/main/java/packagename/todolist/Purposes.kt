package packagename.todolist
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Purposes")
data class Purposes (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "purpose") val purpose: String,
    @ColumnInfo(name = "did") var did: Int
    )