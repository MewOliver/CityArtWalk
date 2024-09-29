package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.cityartwalk.Art

@Database(entities = [Art::class], version = 6)
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

val migration_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Art ADD COLUMN address TEXT;"
        )
    }
}

val migration_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Art ADD COLUMN longitude DECIMAL; ALTER TABLE Art ADD COLUMN latitude DECIMAL;"
        )
    }
}

val migration_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Art ADD COLUMN _longitude DECIMAL; ALTER TABLE Art ADD COLUMN _latitude DECIMAL;"
        )
    }
}

