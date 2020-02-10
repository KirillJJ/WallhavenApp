package app.kl.testapp

import android.app.Application
import android.content.Context
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import app.kl.testapp.di.app.AppComponent
import app.kl.testapp.di.app.DaggerAppComponent
import io.realm.Realm
import javax.inject.Inject

class WallhavenApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .app(this)
            .build()
            .inject(this)
        Realm.init(this)
    }

    companion object {
        fun get(context: Context) = context as WallhavenApp
    }

    override fun androidInjector() = androidInjector
}