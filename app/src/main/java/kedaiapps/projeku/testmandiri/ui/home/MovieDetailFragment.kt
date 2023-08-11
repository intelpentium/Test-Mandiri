package kedaiapps.projeku.testmandiri.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testmandiri.BuildConfig
import kedaiapps.projeku.testmandiri.R
import kedaiapps.projeku.testmandiri.common.UiState
import kedaiapps.projeku.testmandiri.databinding.FragmentMovieDetailBinding
import kedaiapps.projeku.testmandiri.ext.observe
import kedaiapps.projeku.testmandiri.ext.toast
import kedaiapps.projeku.testmandiri.modules.base.BaseFragment
import kedaiapps.projeku.testmandiri.services.entity.ResponseMovieItem
import kedaiapps.projeku.testmandiri.viewmodel.MainViewModel
import kedaiapps.projeku.testmandiri.viewmodel.RepoViewModel

class MovieDetailFragment : BaseFragment() {
    lateinit var mBinding: FragmentMovieDetailBinding
    private val viewModel by viewModels<MainViewModel>()
    private val viewModelRepo by viewModels<RepoViewModel>()
    private val args by navArgs<MovieDetailFragmentArgs>()

    private var title = ""
    private var poster_path = ""
    private var release_date = ""
    private var vote_average = ""
    private var vote_count = ""
    private var overview = ""
    private var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initView()
        handleState()
    }

    private fun initToolbar() {
        mBinding.toolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        mBinding.toolbar.tvTitle.text = "Movie Information"
        mBinding.toolbar.ivFavorite.isVisible = true
    }

    private fun initView() {
        viewModel.movieDetail(args.movieId)
        getFavorite(args.movieId)

        mBinding.toolbar.ivFavorite.setOnClickListener {
            if (status != "") {
                viewModelRepo.updateFavorite(args.movieId, if (status == "1") "0" else "1")

                if (status == "1") {
                    requireActivity().toast("Favorite berhasil dihapus")
                } else {
                    requireActivity().toast("Favorite berhasil ditambahkan")
                }
            } else {
                viewModelRepo.setFavorite(
                    args.movieId,
                    title,
                    poster_path,
                    release_date,
                    vote_average,
                    vote_count,
                    overview,
                    "1"
                )
                requireActivity().toast("Favorite berhasil ditambahkan")
            }
        }

        mBinding.trailer.setOnClickListener {
            findNavController().navigate(
                MovieDetailFragmentDirections.actionMovieDetailFragmentToYoutubeFragment(
                    args.movieId
                )
            )
        }
    }

    private fun handleState() {

        observe(viewModel.responseMovieItem) {
            if (it != null) {
                setData(it)
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.progress.progress.isVisible = true
                UiState.Success -> {
                    mBinding.progress.progress.isVisible = false
                }
                is UiState.Error -> {
                    mBinding.progress.progress.isVisible = false
                    requireActivity().toast(it.message)
                }
            }
        }
    }

    private fun getFavorite(movie_id: Int) {
        observe(viewModelRepo.getFavoriteId(movie_id)) {
            if (it != null) {
                status = it.status
                mBinding.toolbar.ivFavorite.setImageDrawable(
                    requireActivity().getDrawable(
                        if (status == "1") R.drawable.ic_favorite2
                        else R.drawable.ic_favorite
                    )
                )
            }
        }
    }

    private fun setData(data: ResponseMovieItem) {
        title = data.title
        poster_path = data.poster_path
        release_date = data.release_date
        vote_average = (data.vote_average ?: "-")
        vote_count = (data.vote_count ?: "-")
        overview = data.overview

        Glide.with(this).load(BuildConfig.API_BASE_URL_IMAGE + "" + poster_path)
            .apply(
                RequestOptions()
                    .transform(RoundedCorners(16))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
            ).into(mBinding.posterPath)

        mBinding.title.text = title
        mBinding.releaseDate.text = release_date
        mBinding.voteCount.text = "Total Rating : $vote_average"
        mBinding.voteAverage.text = "Reviews :$vote_count"
        mBinding.overview.text = overview
    }
}