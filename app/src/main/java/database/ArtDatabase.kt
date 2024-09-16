package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.cityartwalk.Art

@Database(entities = [Art::class], version = 3)
@TypeConverters(ArtTypeConverters::class)

abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao(): ArtDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(databse: SupportSQLiteDatabase) {
        databse.execSQL(
            "ALTER TABLE Art ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}

val migration_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Art ADD COLUMN photoFileName TEXT"
        )
    }
}