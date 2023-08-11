package kedaiapps.projeku.testmandiri.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kedaiapps.projeku.testmandiri.common.UiState
import kedaiapps.projeku.testmandiri.databinding.FragmentReviewBinding
import kedaiapps.projeku.testmandiri.ext.observe
import kedaiapps.projeku.testmandiri.ext.toast
import kedaiapps.projeku.testmandiri.modules.base.BaseFragment
import kedaiapps.projeku.testmandiri.services.entity.ResponseReviewDetail
import kedaiapps.projeku.testmandiri.ui.home.adapter.AdapterReview
import kedaiapps.projeku.testmandiri.viewmodel.MainViewModel

class ReviewFragment : BaseFragment() {
    lateinit var mBinding: FragmentReviewBinding
    private val viewModel by viewModels<MainViewModel>()
    private val args by navArgs<ReviewFragmentArgs>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterReview(::onClick)
    }

    // paggination
    private var search = ""
    private var page = 1
    private var totalPage = 10
    private var isLoading = true
    private val layoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewBinding.inflate(inflater, container, false)
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
        mBinding.toolbar.tvTitle.text = "Review"
    }

    private fun initView() {
        viewModel.review(args.movieId, page)

        // paggination
        mBinding.rv.layoutManager = layoutManager
        mBinding.rv.adapter = adapter
        mBinding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1 && page < totalPage) {
                    page++
                    isLoading = false
                    mBinding.progress.isVisible = true

                    viewModel.movie(page, args.movieId)
                }
            }
        })
    }

    private fun handleState() {

        //paggination
        observe(viewModel.responseReview) {
            if (it != null) {
                totalPage = it.total_pages
                adapter.image = args.posterPath
                if (page == 1) {
                    adapter.clearData()
                    adapter.insertData(it.results)
                } else {
                    adapter.insertData(it.results)
                    isLoading = true
                    mBinding.progress.isVisible = false
                }
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.progress.isVisible = true
                UiState.Success -> {
                    mBinding.progress.isVisible = false
                }
                is UiState.Error -> {
                    mBinding.progress.isVisible = false
                    requireActivity().toast(it.message)
                }
            }
        }
    }

    private fun onClick(data: ResponseReviewDetail) {
        mBinding.rv.layoutManager = null
        findNavController().navigate(
            ReviewFragmentDirections.actionReviewFragmentToMovieDetailFragment(
                args.movieId
            )
        )
    }
}