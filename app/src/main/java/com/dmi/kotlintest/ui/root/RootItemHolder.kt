package com.dmi.kotlintest.ui.root

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.dmi.kotlintest.R
import com.dmi.kotlintest.common.BaseRecyclerHolder
import com.dmi.kotlintest.common.DateFormats
import com.dmi.kotlintest.common.toString
import com.dmi.kotlintest.data.model.DataInfo
import kotlinx.android.synthetic.main.item_root_info.view.*

class RootItemHolder (
        val view: View
) : BaseRecyclerHolder<DataInfo>(view) {

    override fun bindView(data: DataInfo, onItemClick: ((item: DataInfo, position: Int) -> Unit)?) {
        view.run {
            infoTitle.text = data.title.orEmpty()
            infoText.text = data.text.orEmpty()

            data.image?.let {
                Glide.with(context)
                        .load(it)
                        .into(infoImage)
            }

            infoTime.text = data.date.toString(DateFormats.VISUAL)
            setOnClickListener { onItemClick?.invoke(data, adapterPosition) }
        }
    }
}