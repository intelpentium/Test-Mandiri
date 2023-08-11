package kedaiapps.projeku.testmandiri.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testmandiri.BuildConfig
import kedaiapps.projeku.testmandiri.common.ActionLiveData
import kedaiapps.projeku.testmandiri.common.UiState
import kedaiapps.projeku.testmandiri.ext.errorMesssage
import kedaiapps.projeku.testmandiri.services.entity.*
import kedaiapps.projeku.testmandiri.services.rest.MainRest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRest: MainRest
) : ViewModel() {

    val loadState = ActionLiveData<UiState>()

    val responseGenre = ActionLiveData<List<ResponseGenre>>()
    val responseMovie = ActionLiveData<ResponseMovie>()
    val responseMovieItem = ActionLiveData<ResponseMovieItem>()
    val responseReview = ActionLiveData<ResponseReview>()
    val responseYoutube = ActionLiveData<List<ResponseYoutube>>()
//    val responseSearch = ActionLiveData<List<ResponseHome>>()
//    val responseHomeDetail = ActionLiveData<ResponseHomeDetail>()

    fun home() {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.genre()
                responseGenre.value = response.genres!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun movie(page: Int, withGenres: Int) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.movie(page, withGenres)
                responseMovie.value = response!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.movieDetail(movieId)
                responseMovieItem.value = response!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun review(movieId: Int, page: Int) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.review(movieId, page)
                responseReview.value = response!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun youtube(movieId: Int) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.youtube(movieId)
                responseYoutube.value = response.results!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

//    fun search(data: String) {
//        viewModelScope.launch {
//            loadState.sendAction(UiState.Loading)
//            try {
//                val response = mainRest.search(data, BuildConfig.API_KEY)
//                responseSearch.value = response.products!!
//                loadState.sendAction(UiState.Success)
//            } catch (e: Exception) {
//                loadState.sendAction(UiState.Error(e.errorMesssage))
//            }
//        }
//    }
//
//    fun homeDetail(id: String) {
//        viewModelScope.launch {
//            loadState.sendAction(UiState.Loading)
//            try {
//                val response = mainRest.homeDetail(id, BuildConfig.API_KEY)
//                responseHomeDetail.value = response!!
//                loadState.sendAction(UiState.Success)
//            } catch (e: Exception) {
//                loadState.sendAction(UiState.Error(e.errorMesssage))
//            }
//        }
//    }
}