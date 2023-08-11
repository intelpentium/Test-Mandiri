package kedaiapps.projeku.testmandiri.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kedaiapps.projeku.testmandiri.common.UiState
import kedaiapps.projeku.testmandiri.databinding.FragmentGenreBinding
import kedaiapps.projeku.testmandiri.ext.observe
import kedaiapps.projeku.testmandiri.ext.toast
import kedaiapps.projeku.testmandiri.modules.base.BaseFragment
import kedaiapps.projeku.testmandiri.services.entity.ResponseGenre
import kedaiapps.projeku.testmandiri.ui.home.adapter.AdapterGenre
import kedaiapps.projeku.testmandiri.viewmodel.MainViewModel

class GenreFragment : BaseFragment() {
    lateinit var mBinding: FragmentGenreBinding
    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterGenre(::onClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentGenreBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initView()
        handleState()
    }

    private fun initToolbar() {
        mBinding.toolbar.ivBack.isVisible = false
        mBinding.toolbar.tvTitle.text = "Genre"
    }

    private fun initView() {
        viewModel.home()

        mBinding.rv.adapter = adapter
    }

    private fun handleState() {

        observe(viewModel.responseGenre) {
            if (it != null) {
                adapter.clearData()
                adapter.insertData(it)
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

    private fun onClick(data: ResponseGenre) {
        findNavController().navigate(GenreFragmentDirections.actionGenreFragmentToMovieFragment(data.id))
    }
}