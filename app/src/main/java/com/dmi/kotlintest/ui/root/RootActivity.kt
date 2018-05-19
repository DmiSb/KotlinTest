package com.dmi.kotlintest.ui.root

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.Animation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dmi.kotlintest.R
import com.dmi.kotlintest.common.BaseActivity
import com.dmi.kotlintest.common.BaseRecyclerAdapter
import com.dmi.kotlintest.common.ExtrasKey
import com.dmi.kotlintest.data.model.DataInfo
import com.dmi.kotlintest.ui.detail.DetailActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_root.*
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation



class RootActivity : BaseActivity(), RootView {

    @InjectPresenter
    lateinit var presenter: RootPresenter

    @ProvidePresenter
    fun providePresenter() = RootPresenter(injector)

    override fun provideOverridingModule() = Kodein.Module {
        bind<KodeinInjector>() with instance(this@RootActivity.injector)
    }

    private lateinit var adapter: BaseRecyclerAdapter<DataInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        initView()
        initList()
    }

    private fun initView() {
        rootRefreshBtn.setOnClickListener { refresh() }
        rootServerSort.setOnClickListener { presenter.setServerSort() }
        rootDateSort.setOnClickListener { presenter.setDateSort() }
    }

    private fun initList() {
        rootList.layoutManager = LinearLayoutManager(this)
        adapter = BaseRecyclerAdapter(
                layout = R.layout.item_root_info,
                items = arrayListOf(),
                holderFactory = { view -> RootItemHolder(view) },
                onItemClick = { item, _ -> showDetail(item) }
        )
        rootList.adapter = adapter
    }

    override fun setList(items: List<DataInfo>) {
        adapter.setDataSet(items)
    }

    override fun onResume() {
        super.onResume()
        presenter.startTimer()
    }

    override fun onPause() {
        super.onPause()
        presenter.stopTimer()
    }

    private fun showDetail(info: DataInfo) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(ExtrasKey.DETAIL.name, info)
        startActivity(intent)
    }

    override fun setDateSort(dateSort: Boolean) {
        when (dateSort) {
            false -> rootServerSort.isChecked = true
            true -> rootDateSort.isChecked = true
        }
    }

    private fun refresh() {
        val anim = RotateAnimation(
                0.0f,
                180.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        )
        anim.interpolator = LinearInterpolator()
        anim.duration = 300
        rootRefreshBtn.startAnimation(anim)
        presenter.loadDataInfo()
    }
}
