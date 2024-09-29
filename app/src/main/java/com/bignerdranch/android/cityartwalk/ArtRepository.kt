package com.bignerdranch.android.cityartwalk

import android.content.Context
import androidx.room.Room
import database.ArtDatabase
import database.migration_1_2
import database.migration_2_3
import database.migration_3_4
import database.migration_4_5
import database.migration_5_6
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "art-database"

class ArtRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: ArtDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ArtDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(migration_1_2, migration_2_3)
        .addMigrations(migration_2_3, migration_3_4)
        .addMigrations(migration_3_4, migration_4_5)
        .addMigrations(migration_4_5, migration_5_6)
        .build()

    fun getArts(): Flow<List<Art>> = database.artDao().getArts()

    suspend fun getArt(id: UUID): Art = database.artDao().getArt(id)

    fun updateArt(art: Art) {
        coroutineScope.launch {
            database.artDao().updateArt(art)
        }
    }

    suspend fun addArt(art: Art) {
        database.artDao().addArt(art)
    }

    companion object {
        private var INSTANCE: ArtRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ArtRepository(context)
            }
        }

        fun get(): ArtRepository {
            return INSTANCE ?:
            throw IllegalStateException("ArtRepository must be initialized")
        }
    }
}