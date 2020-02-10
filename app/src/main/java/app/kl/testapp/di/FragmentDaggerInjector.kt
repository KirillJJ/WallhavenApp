package app.kl.testapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentDaggerInjector @Inject constructor(): FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        if (f is MvpAppCompatFragment) AndroidSupportInjection.inject(f)
    }
}