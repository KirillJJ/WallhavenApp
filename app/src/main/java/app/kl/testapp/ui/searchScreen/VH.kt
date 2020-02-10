package app.kl.testapp.ui.searchScreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class VH<T>(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    open fun fillViews(data: T) {}
    open fun onRecycled() {}
}