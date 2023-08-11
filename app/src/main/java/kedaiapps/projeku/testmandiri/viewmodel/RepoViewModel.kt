package kedaiapps.projeku.testmandiri.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testmandiri.db.MandiriDB
import kedaiapps.projeku.testmandiri.db.table.FavoriteTable
import kedaiapps.projeku.testmandiri.ext.ioThread
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    val application: Application
) : ViewModel() {

    val db = MandiriDB.getDatabase(this.application)

    //======================= Local Database Favorite ===================
    fun setFavorite(
        movie_id: Int,
        title: String,
        poster_path: String,
        release_date: String,
        vote_average: String,
        vote_count: String,
        overview: String,
        status: String
    ) {
        ioThread {
            db.daoFavorite().insert(
                FavoriteTable(
                    0,
                    movie_id,
                    title,
                    poster_path,
                    release_date,
                    vote_average,
                    vote_count,
                    overview,
                    status
                )
            )
        }
    }

    fun getFavorite(): LiveData<List<FavoriteTable>> {
        return db.daoFavorite().getAll("1")
    }

    fun getFavoriteId(movie_id: Int): LiveData<FavoriteTable> {
        return db.daoFavorite().getById(movie_id)
    }

    fun updateFavorite(movie_id: Int, status: String) {
        ioThread {
            db.daoFavorite().update(movie_id, status)
        }
    }

    fun deleteFavorite() {
        ioThread {
            db.daoFavorite().deleteAll()
        }
    }
}