package app.kl.testapp.di.activity

import app.kl.testapp.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [ActivitySubcomponent::class], includes = [AndroidSupportInjectionModule::class])
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ClassKey(value = MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(factory: ActivitySubcomponent.Factory): AndroidInjector.Factory<*>

}