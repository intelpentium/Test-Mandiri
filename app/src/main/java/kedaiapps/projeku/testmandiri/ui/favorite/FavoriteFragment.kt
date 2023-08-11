package kedaiapps.projeku.testmandiri.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kedaiapps.projeku.testmandiri.databinding.FragmentFavoriteBinding
import kedaiapps.projeku.testmandiri.db.table.FavoriteTable
import kedaiapps.projeku.testmandiri.ext.observe
import kedaiapps.projeku.testmandiri.modules.base.BaseFragment
import kedaiapps.projeku.testmandiri.ui.favorite.adapter.AdapterFavorite
import kedaiapps.projeku.testmandiri.viewmodel.RepoViewModel

class FavoriteFragment : BaseFragment() {
    lateinit var mBinding: FragmentFavoriteBinding
    private val viewModel by viewModels<RepoViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterFavorite(::onClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
        mBinding.toolbar.tvTitle.text = "Favorite"
    }

    private fun initView() {
        mBinding.rv.adapter = adapter
    }

    private fun handleState() {
        observe(viewModel.getFavorite()) {
            if (it != null) {
                adapter.clearData()
                adapter.insertData(it)
            }
        }
    }

    private fun onClick(data: FavoriteTable) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailFragment(
                data.movie_id
            )
        )
    }
}