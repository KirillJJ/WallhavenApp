package app.kl.testapp.di.activity

import dagger.Subcomponent
import dagger.android.AndroidInjector
import app.kl.testapp.MainActivity
import app.kl.testapp.di.ActivityScope
import app.kl.testapp.di.search.SearchModule
import app.kl.testapp.di.wallpaperScreen.WallpaperScreenModule

@ActivityScope
@Subcomponent(modules = [SearchModule::class, WallpaperScreenModule::class, RealmModule::class])
interface ActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}