package app.kl.testapp.ui.searchScreen

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.kl.testapp.R
import app.kl.testapp.db.WallpaperRealmObject
import app.kl.testapp.ui.searchScreen.VH
import app.kl.testapp.util.inflate
import com.bumptech.glide.Glide
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_wallpaper.*
import javax.inject.Inject

class WallpaperRealmAdapter @Inject constructor(
    private val context: Context
) : RealmRecyclerViewAdapter<WallpaperRealmObject, VH<WallpaperRealmObject>>(null, true, false) {

    lateinit var onClickListener: (WallpaperRealmObject) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<WallpaperRealmObject> =
        RealmVH(parent.inflate(R.layout.item_wallpaper))

    override fun onBindViewHolder(holder: VH<WallpaperRealmObject>, position: Int) {
        holder.fillViews(getItem(position)!!)
    }

    inner class RealmVH(view: View) : VH<WallpaperRealmObject>(view) {
        override fun fillViews(data: WallpaperRealmObject) {
            img.setOnClickListener { onClickListener(data) }
            Glide.with(context)
                .load(data.largeThumb)
                .thumbnail(
                    Glide.with(context)
                        .load(data.smallThumb)
                )
                .into(img)
        }

        override fun onRecycled() {
            Glide.with(context).clear(img)
        }
    }
}