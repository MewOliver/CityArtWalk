package com.bignerdranch.android.cityartwalk

import android.content.Context
import androidx.room.Room
import database.ArtDatabase
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
        .build()

    fun getArts(): Flow<List<Art>> = database.artDao().getArts()

    suspend fun getArt(id: UUID): Art = database.artDao().getArt(id)

    fun updateArt(art: Art) {
        coroutineScope.launch {
            database.artDao().updateArt(art)
        }
    }

    fun deleteArt(art: Art) {
        coroutineScope.launch {
            database.artDao().deleteArt(art)
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