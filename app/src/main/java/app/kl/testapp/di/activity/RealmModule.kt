package app.kl.testapp.di.activity

import app.kl.testapp.di.ActivityScope
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
abstract class RealmModule {
    companion object {
        @ActivityScope
        @Provides
        fun provideRealm(): Realm = Realm.getDefaultInstance()
    }
}