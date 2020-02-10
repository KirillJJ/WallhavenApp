package app.kl.testapp.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import app.kl.testapp.WallhavenApp
import app.kl.testapp.di.activity.ActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(context: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: WallhavenApp)
}