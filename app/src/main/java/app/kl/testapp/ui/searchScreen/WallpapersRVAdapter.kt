package app.kl.testapp.ui.searchScreen

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import app.kl.testapp.R
import app.kl.testapp.api.responses.WallpaperData
import app.kl.testapp.util.inflate
import app.kl.testapp.util.invokeOnChangeDelegate
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_wallpaper.*
import javax.inject.Inject
import kotlin.properties.Delegates

class WallpapersRVAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<WallpaperVh>() {

    init {
        setHasStableIds(true)
    }

    private val handler = Handler()

    lateinit var onClickListener: (WallpaperData) -> Unit

    lateinit var nextScreenRequestListener: () -> Unit

    var values by Delegates.observable(emptyList<WallpaperData>()) { _, _, _ ->
        handler.post(this::notifyDataSetChanged)
    }

    var showLoadingItem by invokeOnChangeDelegate(false) { _, new ->
        handler.post {
            if (new) notifyItemInserted(values.size) else notifyItemRemoved(values.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperVh =
        if (viewType == LOADING_ITEM) PbVh(
            parent.inflate(R.layout.loading_rv_item)
        )
        else ImgVh(parent.inflate(R.layout.item_wallpaper))

    override fun getItemViewType(position: Int): Int =
        if (isLoadingItemPosition(position)) LOADING_ITEM else IMG_ITEM

    override fun onBindViewHolder(holder: WallpaperVh, position: Int) {
        if (!isLoadingItemPosition(position)) holder.fillViews(values[position])
        if (needToRequestNextPage(position)) nextScreenRequestListener()
    }

    override fun onViewRecycled(holder: WallpaperVh) {
        holder.onRecycled()
        super.onViewRecycled(holder)
    }

    override fun getItemId(position: Int): Long =
        if (isLoadingItemPosition(position)) Long.MAX_VALUE
        else values[position].id.hashCode().toLong()

    override fun getItemCount(): Int =
        if (showLoadingItem) values.size + 1 else values.size

    private fun isLoadingItemPosition(position: Int): Boolean = showLoadingItem && position == values.size

    private fun needToRequestNextPage(position: Int): Boolean = values.size - position == 10

    class PbVh(view: View) : VH<WallpaperData>(view)

    inner class ImgVh(
        override val containerView: View
    ) : VH<WallpaperData>(containerView), LayoutContainer {

        private var itemId: String? = null

        override fun fillViews(data: WallpaperData) {
            if (itemId == data.id) return
            img.setOnClickListener { onClickListener(data) }
            itemId = data.id
            Glide.with(context)
                .load(data.thumbnails.large)
                .thumbnail(
                    Glide.with(context)
                        .load(data.thumbnails.small)
                )
                .into(img)
        }

        override fun onRecycled() {
            itemId = null
            Glide.with(context).clear(img)
        }
    }

    companion object {
        private const val LOADING_ITEM = Int.MAX_VALUE
        private const val IMG_ITEM = 0
    }
}

typealias WallpaperVh = VH<WallpaperData>
