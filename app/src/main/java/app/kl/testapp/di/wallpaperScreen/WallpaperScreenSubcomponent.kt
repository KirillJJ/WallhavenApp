package app.kl.testapp.di.wallpaperScreen

import app.kl.testapp.di.FragmentScope
import app.kl.testapp.ui.WallpaperFragment
import app.kl.testapp.ui.searchScreen.WallpaperListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent
interface WallpaperScreenSubcomponent : AndroidInjector<WallpaperFragment> {
    @Subcomponent.Factory
    interface WallpaperFragmentFactory : AndroidInjector.Factory<WallpaperFragment>
}