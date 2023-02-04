package packagename.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Purposes::class], version = 4)
abstract class PurposeDatabase:RoomDatabase() {
    companion object {
        private var instance: PurposeDatabase? = null
        fun getInstance(context: Context): PurposeDatabase{
            instance?.let {
                return it
            }

            val db = Room.databaseBuilder(context,PurposeDatabase::class.java,"ToDo.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
            instance = db
            return db
        }
    }
    abstract fun getPurposeDao():PurposeDao
}