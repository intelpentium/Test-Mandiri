package kedaiapps.projeku.testmandiri.ui

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kedaiapps.projeku.testmandiri.R
import kedaiapps.projeku.testmandiri.databinding.ActivityMainBinding
import kedaiapps.projeku.testmandiri.ext.toast
import kedaiapps.projeku.testmandiri.modules.base.BaseActivity

class MainActivity : BaseActivity() {
    lateinit var mBinding: ActivityMainBinding

    // --- untuk back to close app ---
    private var mRecentlyBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navController = findNavController(R.id.navHost)
        navController.setGraph(R.navigation.navigation)

        mBinding.bottomNav.setupWithNavController(navController)
        mBinding.bottomNav.itemIconTintList = null
        mBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    mRecentlyBackPressed = false
                    navController.navigate(R.id.genreFragment)
                }
                R.id.favorite -> {
                    mRecentlyBackPressed = false
                    navController.navigate(R.id.favoriteFragment)
                }
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.genreFragment -> {
                    mBinding.bottomNav.menu.findItem(R.id.home).isChecked = true;
                    showBottomNav()
                }
                R.id.favoriteFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav(){
        mBinding.bottomNav.isVisible = true
    }

    private fun hideBottomNav(){
        mBinding.bottomNav.isVisible = false
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.navHost)

        if(navController.graph.startDestinationId == navController.currentDestination?.id){
            if (mRecentlyBackPressed) {
                ActivityCompat.finishAffinity(this)
            } else {
                toast("Tekan sekali lagi untuk keluar")
                mRecentlyBackPressed = true
            }
        }else {
            super.onBackPressed()
            mRecentlyBackPressed = false
        }
    }
}