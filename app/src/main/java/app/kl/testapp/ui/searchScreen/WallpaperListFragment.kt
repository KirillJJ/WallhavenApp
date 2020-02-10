package app.kl.testapp.ui.searchScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import app.kl.testapp.R
import app.kl.testapp.api.responses.WallpaperData
import app.kl.testapp.db.WallpaperRealmObject
import app.kl.testapp.presentation.wallpaperListScreen.ListState
import app.kl.testapp.presentation.wallpaperListScreen.WallpaperListPresenter
import app.kl.testapp.presentation.wallpaperListScreen.WallpaperListView
import app.kl.testapp.ui.WallpaperFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_wallpapers_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class WallpaperListFragment : MvpAppCompatFragment(R.layout.fragment_wallpapers_list), WallpaperListView {

    @Inject
    lateinit var presenterProvider: Provider<WallpaperListPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var rvAdapter: WallpapersRVAdapter
    @Inject
    lateinit var realmAdapter: WallpaperRealmAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realmAdapter.onClickListener = { moveToWallpaperFragment(it.id!!) }
        rv.adapter = rvAdapter
        rvAdapter.nextScreenRequestListener = presenter::loadNextPage
        rvAdapter.onClickListener = { moveToWallpaperFragment(it.id) }
        vg_refresh.setOnRefreshListener(presenter::onRefresh)
    }

    override fun showListState(state: ListState) {
        when(state) {
            ListState.Empty -> {
                makeVisible(R.id.tv_background)
                tv_background.setText(R.string.empty_list)
                vg_refresh.isRefreshing = false
            }
            is ListState.Data -> {
                updatePaginationAdapterData(state.list)
                rvAdapter.showLoadingItem = state.withLoading
                state.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
                makeVisible(R.id.vg_refresh)
                vg_refresh.isRefreshing = false
            }
            ListState.Loading -> makeVisible(if (vg_refresh.isRefreshing) 0 else R.id.pb)
            is ListState.Error -> {
                makeVisible(R.id.tv_background)
                tv_background.text = state.error
                vg_refresh.isRefreshing = false
            }
            is ListState.DataFromCache -> {
                makeVisible(R.id.vg_refresh)
                updateRealmAdapterData(state.data)
            }
        }
    }

    private fun moveToWallpaperFragment(id: String) {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, WallpaperFragment.newInstance(id))
            addToBackStack(WallpaperFragment::class.java.name)
        }
    }

    private fun updatePaginationAdapterData(data: List<WallpaperData>) {
        rvAdapter.values = data
        if (rv.adapter != rvAdapter) {
            rv.adapter = rvAdapter
        }
    }

    private fun updateRealmAdapterData(data: RealmResults<WallpaperRealmObject>) {
        if (realmAdapter.data == null) realmAdapter.updateData(data)
        if (rv.adapter != realmAdapter) rv.adapter = realmAdapter
    }

    private fun makeVisible(viewId: Int) {
        pb.isVisible = pb.id == viewId
        tv_background.isVisible = tv_background.id == viewId
    }
}
