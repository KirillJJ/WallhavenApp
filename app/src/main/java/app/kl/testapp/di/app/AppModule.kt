package app.kl.testapp.di.app

import android.app.Application
import android.content.Context
import app.kl.testapp.WallhavenApp
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create
import app.kl.testapp.api.WallhavenApi
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.serialization.UnstableDefault
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    abstract fun mainActivityInjector(): MainActivity
//
//    @ContributesAndroidInjector(modules = [SearchModule::class])
//    abstract fun wallpaperFragmentInjector(): WallpaperListFragment

    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()

        @UseExperimental(UnstableDefault::class)
        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://wallhaven.cc/api/v1/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(Json.nonstrict.asConverterFactory("application/json".toMediaType()))
                .build()

        @Singleton
        @Provides
        @JvmStatic
        fun provideWallhavenApi(retrofit: Retrofit): WallhavenApi = retrofit.create()

        @Singleton
        @Provides
        @JvmStatic
        fun provideApp(app: Application): WallhavenApp = app as WallhavenApp

        @Singleton
        @Provides
        @JvmStatic
        fun provideContext(app: Application): Context = app
    }
}