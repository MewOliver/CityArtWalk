package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.cityartwalk.Art

@Database(entities = [Art::class], version = 1)
@TypeConverters(ArtTypeConverters::class)

abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao(): ArtDao
}
