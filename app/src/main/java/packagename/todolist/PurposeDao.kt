package packagename.todolist
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PurposeDao{
    @Query("SELECT * FROM Purposes")
    suspend fun getAllPurposes(): List<Purposes>

    @Insert(entity = Purposes::class)
    suspend fun addPurpose(purpose: Purposes)

    @Update(entity = Purposes::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePurpose(purpose: Purposes)

    @Delete(entity = Purposes::class)
    suspend fun deletePurpose(purposes: Purposes)
}