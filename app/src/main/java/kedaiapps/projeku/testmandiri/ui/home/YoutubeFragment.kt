package kedaiapps.projeku.testmandiri.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kedaiapps.projeku.testmandiri.common.UiState
import kedaiapps.projeku.testmandiri.databinding.FragmentYoutubeBinding
import kedaiapps.projeku.testmandiri.ext.observe
import kedaiapps.projeku.testmandiri.ext.toast
import kedaiapps.projeku.testmandiri.modules.base.BaseFragment
import kedaiapps.projeku.testmandiri.viewmodel.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kedaiapps.projeku.testmandiri.services.entity.ResponseYoutube

class YoutubeFragment : BaseFragment() {
    lateinit var mBinding: FragmentYoutubeBinding
    private val viewModel by viewModels<MainViewModel>()
    private val args by navArgs<YoutubeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentYoutubeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleState()
    }

    private fun initView() {
        viewModel.youtube(args.movieId)
    }

    private fun handleState() {

        observe(viewModel.responseYoutube) {
            if (it != null) {
                setData(it)
            }
        }

        // loading
//        observe(viewModel.loadState) {
//            when (it) {
//                UiState.Loading -> mBinding.progress.progress.isVisible = true
//                UiState.Success -> {
//                    mBinding.progress.progress.isVisible = false
//                }
//                is UiState.Error -> {
//                    mBinding.progress.progress.isVisible = false
//                    requireActivity().toast(it.message)
//                }
//            }
//        }
    }

    private fun setData(data: List<ResponseYoutube>) {
        mBinding.youTubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(data[0].key, 0f)
            }
        })
    }
}