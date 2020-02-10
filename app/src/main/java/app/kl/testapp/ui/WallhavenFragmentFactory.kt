package app.kl.testapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import app.kl.testapp.di.ActivityScope
import app.kl.testapp.ui.searchScreen.WallpaperListFragment
import javax.inject.Inject

@ActivityScope
class WallhavenFragmentFactory @Inject constructor(): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when(className) {
            WallpaperListFragment::class.java.name -> WallpaperListFragment()
            WallpaperFragment::class.java.name -> WallpaperFragment()
           else -> super.instantiate(classLoader, className)
        }
}