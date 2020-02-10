package app.kl.testapp.ui

import android.os.Bundle
import androidx.core.view.isVisible
import app.kl.testapp.R
import app.kl.testapp.db.WallpaperRealmObject
import app.kl.testapp.presentation.wallpaperScreen.WallpaperPresenter
import app.kl.testapp.presentation.wallpaperScreen.WallpaperView
import app.kl.testapp.util.getArg
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class WallpaperFragment : MvpAppCompatFragment(R.layout.fragment_wallpaper), WallpaperView {

    @Inject
    lateinit var presenterProvider: Provider<WallpaperPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setWallpaperId(getArg(WALLPAPER_ID))
    }

    override fun showLoading(show: Boolean) {
        pb.isVisible = show
    }

    override fun showWallpaper(wallpaper: WallpaperRealmObject) {
        Glide.with(this)
            .load(wallpaper.uri)
            .error(
                Glide.with(this)
                    .load(wallpaper.largeThumb)
            )
            .thumbnail(
                Glide.with(this)
                    .load(wallpaper.originalThumb)
                    .fitCenter()
            )
            .fitCenter()
            .into(img)
    }

    companion object {
        private const val WALLPAPER_ID = "wallpaperId"
        @JvmStatic
        fun newInstance(wallpaperId: String) =
            WallpaperFragment().apply {
                arguments = Bundle().apply {
                    putString(WALLPAPER_ID, wallpaperId)
                }
            }
    }
}
