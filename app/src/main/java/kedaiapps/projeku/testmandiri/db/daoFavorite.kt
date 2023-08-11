package kedaiapps.projeku.testmandiri.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kedaiapps.projeku.testmandiri.db.table.FavoriteTable

@Dao
interface daoFavorite {
    @Query("SELECT * FROM FavoriteTable WHERE status=:status")
    fun getAll(status: String) : LiveData<List<FavoriteTable>>

    @Query("SELECT * FROM FavoriteTable WHERE movie_id=:movie_id")
    fun getById(movie_id: Int): LiveData<FavoriteTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: FavoriteTable)

    @Query("UPDATE FavoriteTable SET status=:status WHERE movie_id=:movie_id")
    fun update(movie_id: Int, status: String)

    @Query("DELETE FROM FavoriteTable")
    fun deleteAll()
}