package app.kl.testapp.di.search

import app.kl.testapp.di.FragmentScope
import app.kl.testapp.ui.searchScreen.WallpaperListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent
interface SearchSubcomponent : AndroidInjector<WallpaperListFragment> {
    @Subcomponent.Factory
    interface WallpaperListFactory : AndroidInjector.Factory<WallpaperListFragment>
}