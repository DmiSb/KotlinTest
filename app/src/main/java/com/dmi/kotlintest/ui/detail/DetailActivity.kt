package com.dmi.kotlintest.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dmi.kotlintest.R
import com.dmi.kotlintest.common.ExtrasKey
import com.dmi.kotlintest.data.model.DataInfo
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras
        val info = extras.get(ExtrasKey.DETAIL.name) as DataInfo

        initToolBar(info.title.orEmpty())
        initView(info)
    }

    private fun initToolBar(title: String) {
        setSupportActionBar(detailToolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = title
        }
    }

    private fun initView(info: DataInfo) {
        detailTitle.text = info.title
        detailText.text = info.text

        info.image?.let {
            Glide.with(this)
                    .load(it)
                    .into(detailImage)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}