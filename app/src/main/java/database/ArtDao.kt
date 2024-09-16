package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Art
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ArtDao {
    @Query("SELECT * FROM art")
    fun getArts(): Flow<List<Art>>

    @Query("SELECT * FROM art WHERE id=(:id)")
    suspend fun getArt(id: UUID): Art

    @Update
    suspend fun updateArt(art: Art)

    @Insert
    suspend fun addArt(art: Art)
}