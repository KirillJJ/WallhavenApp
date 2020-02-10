package app.kl.testapp.di.wallpaperScreen

import app.kl.testapp.ui.WallpaperFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [WallpaperScreenSubcomponent::class])
abstract class WallpaperScreenModule {

    @Binds
    @IntoMap
    @ClassKey(value = WallpaperFragment::class)
    abstract fun bindWallpaperFragmentInjectorFactory(factory: WallpaperScreenSubcomponent.WallpaperFragmentFactory): AndroidInjector.Factory<*>
}