package app.kl.testapp.di.search

import app.kl.testapp.ui.WallpaperFragment
import app.kl.testapp.ui.searchScreen.WallpaperListFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [SearchSubcomponent::class])
abstract class SearchModule {

    @Binds
    @IntoMap
    @ClassKey(value = WallpaperListFragment::class)
    abstract fun bindWallpaperListFragmentInjectorFactory(factory: SearchSubcomponent.WallpaperListFactory): AndroidInjector.Factory<*>
}