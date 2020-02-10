package app.kl.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import app.kl.testapp.di.FragmentDaggerInjector
import app.kl.testapp.ui.WallhavenFragmentFactory
import app.kl.testapp.ui.searchScreen.WallpaperListFragment
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.realm.Realm
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject
    lateinit var fragmentFactory: WallhavenFragmentFactory
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var realm: Realm
    @Inject
    lateinit var fragmentInjector: FragmentDaggerInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        supportFragmentManager.apply {
            registerFragmentLifecycleCallbacks(fragmentInjector, false)
            addOnBackStackChangedListener(this@MainActivity::onBackStackEntryChanged)
            commit {
                add<WallpaperListFragment>(R.id.fragment_container)
                addToBackStack(WallpaperListFragment::class.java.name)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            realm.close()
            supportFragmentManager.apply {
                unregisterFragmentLifecycleCallbacks(fragmentInjector)
                removeOnBackStackChangedListener(this@MainActivity::onBackStackEntryChanged)
            }
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(applicationContext).onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).onLowMemory()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onSupportNavigateUp()
    }

    private fun onBackStackEntryChanged() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 1)
    }
}
