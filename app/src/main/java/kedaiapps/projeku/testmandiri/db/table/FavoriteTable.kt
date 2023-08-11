package kedaiapps.projeku.testmandiri.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movie_id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: String,
    val vote_count: String,
    val overview: String,
    val status: String,
)