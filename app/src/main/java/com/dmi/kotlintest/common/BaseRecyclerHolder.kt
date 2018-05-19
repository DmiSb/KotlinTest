package com.dmi.kotlintest.common

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseRecyclerHolder<T>(
        view: View
): RecyclerView.ViewHolder(view) {
    abstract fun bindView(data: T, onItemClick: ((item: T, position: Int) -> Unit)?)
}