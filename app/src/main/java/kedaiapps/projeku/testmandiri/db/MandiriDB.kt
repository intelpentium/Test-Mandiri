package kedaiapps.projeku.testmandiri.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kedaiapps.projeku.testmandiri.db.table.FavoriteTable

@Database(entities = [FavoriteTable::class], version = 1)
abstract class MandiriDB : RoomDatabase(){

    companion object {
        private var INSTANCE: MandiriDB? = null

        fun getDatabase(context: Context): MandiriDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MandiriDB::class.java, "MandiriDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MandiriDB
        }
    }

    abstract fun daoFavorite() : daoFavorite
}