package kedaiapps.projeku.testmandiri.common.storage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(private val prefs: SharedPreferences) {

    var token: String by PreferenceData(prefs, "token", "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOGJjYTBhYTc2OWE4ZWVjNWQ0YjNhN2RlZGIwMGY4MiIsInN1YiI6IjYwNTJmZTAwMWFkOTNiMDAyODMzMzUxMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3ZZBXIAmREn047pZ_wy77AEskdfVPYUamMoqfqPn7bc")
    var username: String by PreferenceData(prefs, "username", "")

    fun clear() {
        prefs.edit().clear().apply()
    }
}