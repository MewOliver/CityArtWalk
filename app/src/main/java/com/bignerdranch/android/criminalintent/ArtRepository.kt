package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.room.Room
import database.ArtDatabase
import database.migration_1_2
import database.migration_2_3
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